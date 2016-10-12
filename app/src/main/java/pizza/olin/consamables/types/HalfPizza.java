package pizza.olin.consamables.types;

import java.util.ArrayList;

/**
 * Created by Sam on 10/11/2016.
 */

public class HalfPizza {

    private String uid;
    private User user;
    private ArrayList<Topping> toppings;

    public HalfPizza() { }

    public HalfPizza(User user, ArrayList<Topping> toppings) {
        this.user = user;
        this.toppings = toppings;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(ArrayList<Topping> toppings) {
        this.toppings = toppings;
    }
}
