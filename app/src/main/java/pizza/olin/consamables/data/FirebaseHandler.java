package pizza.olin.consamables.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pizza.olin.consamables.types.HalfPizza;
import pizza.olin.consamables.types.GroupOrder;
import pizza.olin.consamables.types.OrderItem;
import pizza.olin.consamables.types.WholePizza;

/**
 * An interface for writing data to Firebase.
 */

public class FirebaseHandler {
    private DatabaseReference mDatabase;

    public FirebaseHandler() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void createGroupOrder(int durationMinutes) {
        DatabaseReference newOrderRef = mDatabase.child("orders").push();
        GroupOrder groupOrder = new GroupOrder(durationMinutes);
        newOrderRef.setValue(groupOrder);
    }

    public void addOrder(OrderItem pizza) {
        DatabaseReference newPizzaRef;
        if (pizza instanceof HalfPizza) {
            newPizzaRef = mDatabase.child("halfPizzas").push();
        } else {
            newPizzaRef = mDatabase.child("wholePizzas").push();
        }
        newPizzaRef.setValue(pizza);
    }

    public void closeGroupOrder(String uid) {
        DatabaseReference oldOrderRef = mDatabase.child("orders").child(uid);
        oldOrderRef.child("isClosed").setValue(true);
    }
}
