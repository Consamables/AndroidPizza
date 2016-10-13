package pizza.olin.consamables;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

import pizza.olin.consamables.types.Beverage;
import pizza.olin.consamables.types.GroupOrder;
import pizza.olin.consamables.types.HalfPizza;
import pizza.olin.consamables.types.OrderItem;
import pizza.olin.consamables.types.PizzaOrderType;
import pizza.olin.consamables.types.Topping;
import pizza.olin.consamables.types.WholePizza;

public class OrderBuilder {
    private PizzaOrderType pizzaType;
    private HashMap<Integer, Topping> firstHalfToppings;
    private HashMap<Integer, Topping> secondHalfToppings;
    private Beverage beverage;
    private GroupOrder currentOrder;

    public OrderBuilder() {
        pizzaType = PizzaOrderType.HALF;
        firstHalfToppings = new HashMap<>();
        secondHalfToppings = new HashMap<>();
    }

    public void setCurrentOrder(GroupOrder currentOrder) {
        this.currentOrder = currentOrder;
    }

    public void setPizzaType(PizzaOrderType pizzaType) {
        this.pizzaType = pizzaType;
    }

    public PizzaOrderType getPizzaType() {
        return pizzaType;
    }

    public void setFirstHalfTopping(int index, Topping topping) {
        firstHalfToppings.put(index, topping);
    }

    public void setSecondHalfTopping(int index, Topping topping) {
        secondHalfToppings.put(index, topping);
    }

    public void clearToppings() {
        firstHalfToppings.clear();
        secondHalfToppings.clear();
    }

    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    public OrderItem build() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        OrderItem newItem;

        if (pizzaType == pizzaType.HALF) {
            newItem = new HalfPizza(currentUser, new ArrayList<>(firstHalfToppings.values()));
        } else {
            HalfPizza leftHalf = new HalfPizza(currentUser, new ArrayList<>(firstHalfToppings.values()));
            HalfPizza rightHalf = new HalfPizza(currentUser, new ArrayList<>(secondHalfToppings.values()));
            newItem = new WholePizza(leftHalf, rightHalf);
        }

        newItem.setOrderId(currentOrder.getUid());
        if (beverage != null) {
            newItem.setBeverage(beverage);
        }
        return newItem;
    }
}
