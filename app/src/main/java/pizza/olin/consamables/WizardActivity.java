package pizza.olin.consamables;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

import pizza.olin.consamables.database.SharedPrefsHandler;
import pizza.olin.consamables.types.Topping;

public class WizardActivity extends AppCompatActivity {

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
                ArrayList<Topping> toppings = dataSnapshot.getValue(new ArrayList<Topping>(){}.getClass());
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
}
