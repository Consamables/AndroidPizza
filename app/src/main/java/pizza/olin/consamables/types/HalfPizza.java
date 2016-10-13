package pizza.olin.consamables.types;

import com.annimon.stream.Stream;
import com.annimon.stream.function.BiFunction;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HalfPizza extends OrderItem {
    public static final int BASE_PRICE = 500;
    private FirebaseUser user;
    private ArrayList<Topping> toppings;

    public HalfPizza() { }

    public HalfPizza(FirebaseUser user, ArrayList<Topping> toppings) {
        this.user = user;
        this.toppings = new ArrayList<>();
        for (Topping topping : toppings) {
            if (!topping.getName().equals("None")) {
                this.toppings.add(topping);
            }
        }
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
        if (beverage != null) {
            return BASE_PRICE + beverage.getPriceCents();
        } else {
            return BASE_PRICE;
        }
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
        return Stream.of(toppings)
                .reduce("", new BiFunction<String, Topping, String>() {
                    @Override
                    public String apply(String before, Topping topping) {
                        if (before.length() > 0) { // if not the first element
                            return before + "\n" + topping.getName();
                        }

                        return before + topping.getName();
                    }
                });
    }
}
