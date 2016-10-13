package pizza.olin.consamables.types;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

import pizza.olin.consamables.data.FirebaseHandler;

public class OrderBuilder {
    private PizzaOrderType pizzaType;
    private HashMap<Integer, Topping> firstHalfToppings;
    private HashMap<Integer, Topping> secondHalfToppings;
    private Beverage beverage;

    public OrderBuilder() {
        firstHalfToppings = new HashMap<>();
        secondHalfToppings = new HashMap<>();
    }

    public void setPizzaType(PizzaOrderType pizzaType) {
        this.pizzaType = pizzaType;
    }

    public void setFirstHalfTopping(int index, Topping topping) {
        firstHalfToppings.put(index, topping);
    }

    public void setSecondHalfTopping(int index, Topping topping) {
        secondHalfToppings.put(index, topping);
    }

    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    public OrderItem build() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        OrderItem newItem;

        if (pizzaType == pizzaType.HALF) {
            newItem = new HalfPizza(currentUser, (ArrayList<Topping>) firstHalfToppings.values());
        } else {
            HalfPizza leftHalf = new HalfPizza(currentUser, (ArrayList<Topping>) firstHalfToppings.values());
            HalfPizza rightHalf = new HalfPizza(currentUser, (ArrayList<Topping>) secondHalfToppings.values());
            newItem = new WholePizza(leftHalf, rightHalf);
        }

        if (beverage != null) {
            newItem.setBeverage(beverage);
        }
        return newItem;
    }
}
