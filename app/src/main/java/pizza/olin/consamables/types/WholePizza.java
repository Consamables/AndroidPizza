package pizza.olin.consamables.types;

import java.util.ArrayList;

public class WholePizza extends OrderItem {
    public static final int BASE_PRICE = 1000;
    private HalfPizza leftHalf;
    private HalfPizza rightHalf;

    public WholePizza() { }

    public WholePizza(HalfPizza leftHalf, HalfPizza rightHalf) {
        this.leftHalf = leftHalf;
        this.rightHalf = rightHalf;
    }

    public HalfPizza getLeftHalf() {
        return leftHalf;
    }

    public void setLeftHalf(HalfPizza leftHalf) {
        this.leftHalf = leftHalf;
    }

    public HalfPizza getRightHalf() {
        return rightHalf;
    }

    public void setRightHalf(HalfPizza rightHalf) {
        this.rightHalf = rightHalf;
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
        return leftHalf.getPerson() + " | " + rightHalf.getPerson();
    }

    @Override
    public String getDisplayName() {
        return "Whole Pizza";
    }

    @Override
    public String getDisplayDetails() {
        ArrayList<Topping> leftToppings = leftHalf.getToppings();
        ArrayList<Topping> rightToppings = rightHalf.getToppings();
        int leftMaxLength = getLeftMaxToppingLength();
        int rightMaxLength = getRightMaxToppingLength();

        // TODO: Make this better. Creating side-by-side toppings in progress.

        return leftHalf.getDisplayDetails() + "\n\n" + rightHalf.getDisplayDetails();
    }

    private int getLeftMaxToppingLength() {
        int max = 0;
        for (Topping topping : leftHalf.getToppings()) {
            if (topping.getName().length() > max) {
                max = topping.getName().length();
            }
        }
        return max;
    }

    private int getRightMaxToppingLength() {
        int max = 0;
        for (Topping topping : rightHalf.getToppings()) {
            if (topping.getName().length() > max) {
                max = topping.getName().length();
            }
        }
        return max;
    }
}
