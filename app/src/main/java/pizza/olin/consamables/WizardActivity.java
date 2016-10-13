package pizza.olin.consamables;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pizza.olin.consamables.data.FirebaseHandler;
import pizza.olin.consamables.data.SharedPrefsHandler;
import pizza.olin.consamables.pages.BeverageSelectPage;
import pizza.olin.consamables.pages.FinishedOrderPage;
import pizza.olin.consamables.pages.HalfOrWholePage;
import pizza.olin.consamables.pages.OrderConfirmationPage;
import pizza.olin.consamables.pages.ToppingSelectPage;
import pizza.olin.consamables.pages.WizardBasicPage;
import pizza.olin.consamables.types.Beverage;
import pizza.olin.consamables.types.GroupOrder;
import pizza.olin.consamables.types.OrderItem;
import pizza.olin.consamables.types.PizzaOrderType;
import pizza.olin.consamables.types.Topping;

public class WizardActivity extends AppCompatActivity
                            implements HalfOrWholePage.PizzaTypeListener, ToppingSelectPage.ToppingSelectListener,
                                       BeverageSelectPage.BeverageTypeListener, OrderConfirmationPage.OrderConfirmationListener,
                                       FinishedOrderPage.FinishedOrderListener {
    private static final String TAG = "WizardActivity";
    private static final String ANONYMOUS = "anonymous";
    private static final int RC_SIGN_IN = 47;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;

    private SharedPrefsHandler prefsHandler;
    private OrderBuilder orderBuilder;
    private ViewPager pager;
    private FirebaseHandler firebaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);

        pager = (ViewPager) findViewById(R.id.pager);
        orderBuilder = new OrderBuilder();
        firebaseHandler = new FirebaseHandler();
        prefsHandler = new SharedPrefsHandler(getPreferences(Context.MODE_PRIVATE));
        fetchFirebaseData();

        final ViewPager pager = (ViewPager) findViewById(R.id.pager);

        assert pager != null;
        ArrayList<Fragment> wizardSteps = new ArrayList<>();
        wizardSteps.add(HalfOrWholePage.newInstance());
        wizardSteps.add(ToppingSelectPage.newInstance());
        wizardSteps.add(BeverageSelectPage.newInstance());
        wizardSteps.add(OrderConfirmationPage.newInstance());
        wizardSteps.add(FinishedOrderPage.newInstance());

        pager.setAdapter(new WizardPagerAdapter(getSupportFragmentManager(), wizardSteps));

        Button previousButton = (Button) findViewById(R.id.prev_button);
        Button nextButton = (Button) findViewById(R.id.next_button);

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem() - 1);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem() + 1);
            }
        });

        StepperIndicator indicator = (StepperIndicator) findViewById(R.id.stepper_indicator);
        assert indicator != null;
        // We keep last page for a "finishing" page
        indicator.setViewPager(pager, true);

        indicator.addOnStepClickListener(new StepperIndicator.OnStepClickListener() {
            @Override
            public void onStepClicked(int step) {
                pager.setCurrentItem(step, true);
            }
        });

        mUsername = ANONYMOUS;
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivityForResult(
                    // Get an instance of AuthUI based on the default app
                    AuthUI.getInstance().createSignInIntentBuilder().build(),
                    RC_SIGN_IN);
        } else {
            mUsername = mFirebaseUser.getDisplayName();

            // see if user is using an Olin email (TODO: make this better)
            if (mFirebaseUser.getEmail() != null) {
                String mEmail = mFirebaseUser.getEmail();
                if (!mEmail.endsWith("@students.olin.edu")) {
                    Toast.makeText(this, getString(R.string.no_olin_email), Toast.LENGTH_LONG)
                            .show();
                } else {
                    if (!mFirebaseUser.isEmailVerified()) {
                        Toast.makeText(this, getString(R.string.email_not_verified), Toast.LENGTH_LONG)
                                .show();
                        mFirebaseUser.sendEmailVerification();
                    }
                }
            }
        }
    }

    private void fetchFirebaseData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference toppingsRef = database.getReference("toppings");

        toppingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Topping> toppings = new ArrayList<>();
                toppings.add(new Topping("None")); // I don't want a topping option
                for (DataSnapshot toppingSnapshot : dataSnapshot.getChildren()) {
                    toppings.add(new Topping(toppingSnapshot.getValue(String.class)));
                }
                prefsHandler.setToppings(toppings);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final DatabaseReference beveragesRef = database.getReference("beverages");

        beveragesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Beverage> beverages = new ArrayList<>();
                beverages.add(new Beverage("No Thanks", 0));
                for (DataSnapshot beverageSnapshot : dataSnapshot.getChildren()) {
                    beverages.add(beverageSnapshot.getValue(Beverage.class));
                }
                prefsHandler.setBeverages(beverages);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final DatabaseReference orderRef = database.getReference("orders");
        orderRef.orderByChild("startTime/time").limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GroupOrder currentOrder = dataSnapshot.getValue(GroupOrder.class);
                currentOrder.setUid(dataSnapshot.getKey());
                orderBuilder.setCurrentOrder(currentOrder);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wizard, menu);

        // hide by default
        menu.findItem(R.id.action_admin_page).setVisible(false);

        if (mFirebaseUser != null && mFirebaseUser.getEmail() != null && mFirebaseUser.isEmailVerified()) {
            boolean isDanny = mFirebaseUser.getEmail().equals("daniel.wolf@students.olin.edu");
            boolean isSam = mFirebaseUser.getEmail().equals("sam@students.olin.edu");

            if (isDanny || isSam) { // yeah that's right
                menu.findItem(R.id.action_admin_page).setVisible(true);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_admin_page:
                Intent intent = new Intent(this, AdminPageActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_sign_out:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                startActivity(new Intent(WizardActivity.this, WizardActivity.class));
                                finish();
                            }
                        });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public PizzaOrderType getPizzaType() {
        return orderBuilder.getPizzaType();
    }

    @Override
    public void setPizzaType(PizzaOrderType type) {
        orderBuilder.setPizzaType(type);
        pager.setCurrentItem(pager.getCurrentItem() + 1, true);
    }

    @Override
    public void setFirstHalfTopping(int index, Topping topping) {
        orderBuilder.setFirstHalfTopping(index, topping);
    }

    @Override
    public void setSecondHalfTopping(int index, Topping topping) {
        orderBuilder.setSecondHalfTopping(index, topping);
    }

    @Override
    public void setBeverage(Beverage beverage) {
        orderBuilder.setBeverage(beverage);
    }

    @Override
    public OrderItem getCurrentOrder() {
        return orderBuilder.build();
    }

    @Override
    public void confirmOrder() {
        firebaseHandler.addOrder(orderBuilder.build());
    }
}
