package pizza.olin.consamables.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pizza.olin.consamables.types.HalfPizza;
import pizza.olin.consamables.types.GroupOrder;
import pizza.olin.consamables.types.WholePizza;

/**
 * An interface for writing data to Firebase.
 */

public class DataHandler {
    private DatabaseReference mDatabase;

    public DataHandler() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void createOrder(int duration) {
        DatabaseReference newOrderRef = mDatabase.child("orders").push();
        GroupOrder groupOrder = new GroupOrder(duration);
        newOrderRef.setValue(groupOrder);
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
