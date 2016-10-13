package pizza.olin.consamables.database;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import pizza.olin.consamables.types.Order;
import pizza.olin.consamables.types.Topping;

/**
 * TODO: Add a class header comment!
 */

public class SharedPrefsHandler {

    private SharedPreferences prefs;
    private Gson gson = new Gson();

    public SharedPrefsHandler(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    private void setValue(String key, Object value) {
        SharedPreferences.Editor editor = prefs.edit();
        String serializedValue = gson.toJson(value);
        editor.putString(key, serializedValue);
        editor.commit();
    }

    public void setToppings(ArrayList<Topping> toppings) {
        setValue("Toppings", toppings);
    }

    public void setCurrentOrder(Order order) {
        setValue("Current Order", order);
    }

    public ArrayList<Topping> getToppings() {
        String serializedToppings = prefs.getString("Toppings", "[]");
        ArrayList<Topping> allToppings = gson.fromJson(serializedToppings, new TypeToken<ArrayList<Topping>>(){}.getType());
        return allToppings;
    }

    public Order getCurrentOrder() {
        String serializedOrder = prefs.getString("Current Order", "");
        Order order = gson.fromJson(serializedOrder, Order.class);
        return order;
    }
}
