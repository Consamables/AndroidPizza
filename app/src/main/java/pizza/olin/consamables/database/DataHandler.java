package pizza.olin.consamables.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pizza.olin.consamables.types.HalfPizza;
import pizza.olin.consamables.types.Order;
import pizza.olin.consamables.types.User;
import pizza.olin.consamables.types.WholePizza;

/**
 * Created by Sam on 10/11/2016.
 */

public class DataHandler {
    private DatabaseReference mDatabase;

    public DataHandler() {
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
