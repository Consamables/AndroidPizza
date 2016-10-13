package pizza.olin.consamables.types;

import com.annimon.stream.Stream;
import com.annimon.stream.function.BiFunction;
import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Function;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HalfPizza extends OrderItem {
    private String uid;
    private FirebaseUser user;
    private ArrayList<Topping> toppings;

    private int priceCents;

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

    @Override
    public int getPriceCents() {
        return priceCents;
    }

    @Override
    public String getPerson() {
        return user.getDisplayName();
    }

    @Override
    public String getDisplayName() {
        return "Half Pizza";
    }

    @Override
    public String getDisplayDetails() {
        return user.getDisplayName() + ": " +
                Stream.of(toppings)
                .reduce("", new BiFunction<String, Topping, String>() {
                    @Override
                    public String apply(String before, Topping topping) {
                        if (before.length() > 0) { // if not the first element
                            return before + ", " + topping.getName();
                        }

                        return before + topping.getName();
                    }
                });
    }
}
