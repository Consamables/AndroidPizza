package pizza.olin.consamables.types;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HalfPizza {

    private String uid;
    private FirebaseUser user;
    private ArrayList<Topping> toppings;

    public HalfPizza() { }

    public HalfPizza(FirebaseUser user, ArrayList<Topping> toppings) {
        this.user = user;
        this.toppings = toppings;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(ArrayList<Topping> toppings) {
        this.toppings = toppings;
    }
}
