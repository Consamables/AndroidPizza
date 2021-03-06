package pizza.olin.consamables;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ListView;
import android.widget.TextView;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import pizza.olin.consamables.data.FirebaseHandler;
import pizza.olin.consamables.types.GroupOrder;
import pizza.olin.consamables.types.HalfPizza;
import pizza.olin.consamables.types.OrderItem;
import pizza.olin.consamables.types.Topping;

public class AdminPageActivity extends AppCompatActivity {

    private static final long UPDATE_TIMER_MS = 1000;
    private FirebaseHandler handler;
    private Optional<GroupOrder> maybeNewestOrder = Optional.empty();
    private FloatingActionButton fab;
    private TextView timeLeft;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        handler = new FirebaseHandler();

        timeLeft = (TextView) findViewById(R.id.time_left_order);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isActiveOrder = false;
                if (maybeNewestOrder.isPresent()) {
                    if (maybeNewestOrder.get().isClosed) {
                        // cool
                        handler.createGroupOrder(30);
                    } else {
                        String uid = maybeNewestOrder.get().getUid();
                        handler.closeGroupOrder(uid);
                    }
                } else {
                    handler.createGroupOrder(30);
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fetchFirebaseData();
    }

    private void updatePizzaButton(boolean isClosed) {
        if (isClosed) {
            // show the last order, have the ability to start a new order
            fab.clearAnimation();
            final LinearInterpolator interpolator = new LinearInterpolator();
            ViewCompat.animate(fab)
                    .rotation(0f)
                    .withLayer()
                    .setDuration(300)
                    .setInterpolator(interpolator).start();

        } else {
            // spin the pizza around to indicate an active order
            final LinearInterpolator interpolator = new LinearInterpolator();
            RotateAnimation animation = new RotateAnimation(
                    0f,
                    360f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f
            );
            animation.setDuration(1000);
            animation.setInterpolator(interpolator);
            animation.setRepeatCount(Animation.INFINITE);
            fab.startAnimation(animation);
        }
    }

    private void updateListView(GroupOrder order) {
        ListView listView = (ListView) findViewById(R.id.order_list);
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        //TODO: get the actual list
        listView.setAdapter(new OrderItemsAdapter(this, orderItems));
    }

    private void updateView() {
        if (maybeNewestOrder.isPresent()) {
            GroupOrder newestOrder = maybeNewestOrder.get();

            updatePizzaButton(newestOrder.isClosed);
            updateListView(newestOrder);
            updateTimer();
        }
    }

    private void updateTimer() {
        if (maybeNewestOrder.isPresent()) {
            GroupOrder newestOrder = maybeNewestOrder.get();

            if (!newestOrder.isClosed) {
                long timeLeftInOrder = (newestOrder.getStartTime().getTime() +
                        (newestOrder.getDurationMinutes() * 60 * 1000)) - new Date().getTime();

                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }

                countDownTimer = new CountDownTimer(timeLeftInOrder, UPDATE_TIMER_MS) {

                    public void onTick(long millisUntilFinished) {
                        timeLeft.setText(String.format(
                                "%02d:%02d",
                                millisUntilFinished / 1000 / 60,
                                millisUntilFinished / 1000 % 60));
                    }

                    public void onFinish() {
                        timeLeft.setText(R.string.order_finished);
                    }
                }.start();
            } else {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }

                timeLeft.setText("Time's up!");
            }
        }
    }

    private void fetchFirebaseData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ordersRef = database.getReference("orders");
        Query query = ordersRef.limitToLast(1);


        final AdminPageActivity thisActivity = this;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<GroupOrder> groupOrders = new ArrayList<>();

                for (DataSnapshot groupOrderSnapshot : dataSnapshot.getChildren()) {
                    String uid = groupOrderSnapshot.getKey();
                    GroupOrder groupOrder = groupOrderSnapshot.getValue(GroupOrder.class);
                    groupOrder.setUid(uid);

                    groupOrders.add(groupOrder);
                }

                Optional<GroupOrder> maybeNewestOrder = Stream.of(groupOrders)
                        .findFirst();

                thisActivity.maybeNewestOrder = maybeNewestOrder;
                thisActivity.updateView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
