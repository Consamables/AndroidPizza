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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pizza.olin.consamables.data.SharedPrefsHandler;
import pizza.olin.consamables.types.PizzaOrderType;
import pizza.olin.consamables.types.Topping;

public class WizardActivity extends AppCompatActivity implements HalfOrWholePage.PizzaTypeListener {
    private static final String TAG = "WizardActivity";
    private static final String ANONYMOUS = "anonymous";
    private static final int RC_SIGN_IN = 47;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;

    private SharedPrefsHandler prefsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);

        final ViewPager pager = (ViewPager) findViewById(R.id.pager);

        assert pager != null;
        ArrayList<Fragment> wizardSteps = new ArrayList<>();
        wizardSteps.add(HalfOrWholePage.newInstance());
        wizardSteps.add(ToppingSelectFragment.newInstance());
        wizardSteps.add(WizardBasicPage.newInstance("Add a drink?"));
        wizardSteps.add(WizardBasicPage.newInstance("Pay"));
        wizardSteps.add(WizardBasicPage.newInstance("You're done!"));


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

            prefsHandler = new SharedPrefsHandler(getPreferences(Context.MODE_PRIVATE));
            fetchFirebaseData();

            // see if user is using an Olin email (TODO: make this better)
            if (mFirebaseUser.getEmail() != null) {
                String mEmail = mFirebaseUser.getEmail();
                if (!mEmail.endsWith("@students.olin.edu")) {
                    Toast.makeText(this, getString(R.string.no_olin_email), Toast.LENGTH_LONG)
                            .show();
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
                ArrayList<Topping> toppings = new ArrayList<Topping>();
                toppings.add(new Topping("None"));
                for (DataSnapshot toppingSnapshot : dataSnapshot.getChildren()) {
                    toppings.add(new Topping(toppingSnapshot.getValue(String.class)));
                }
                prefsHandler.setToppings(toppings);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wizard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

    @Override
    public void setPizzaType(PizzaOrderType type) {

    }
}
