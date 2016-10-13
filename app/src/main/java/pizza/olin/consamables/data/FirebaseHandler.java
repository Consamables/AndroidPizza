package pizza.olin.consamables.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pizza.olin.consamables.types.HalfPizza;
import pizza.olin.consamables.types.Order;
import pizza.olin.consamables.types.WholePizza;

/**
 * An interface for writing data to Firebase.
 */

public class FirebaseHandler {
    private DatabaseReference mDatabase;

    public FirebaseHandler() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void createOrder(int duration) {
        DatabaseReference newOrderRef = mDatabase.child("orders").push();
        Order order = new Order(duration);
        newOrderRef.setValue(order);
    }

    public void addHalf(HalfPizza pizza) {
        DatabaseReference newHalfPizzaRef = mDatabase.child("halfPizzas").push();
        newHalfPizzaRef.setValue(pizza);
    }

    public void addWhole(WholePizza pizza) {
        DatabaseReference newWholePizzaRef = mDatabase.child("wholePizzas").push();
        newWholePizzaRef.setValue(pizza);
    }
}
