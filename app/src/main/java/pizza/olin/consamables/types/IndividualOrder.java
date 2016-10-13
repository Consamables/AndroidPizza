package pizza.olin.consamables.types;

import java.util.ArrayList;

public class IndividualOrder {
    private final ArrayList<OrderItem> orderItems;

    public IndividualOrder(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }
}
