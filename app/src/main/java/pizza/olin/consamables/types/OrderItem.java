package pizza.olin.consamables.types;

public abstract class OrderItem {
    protected Beverage beverage;
    private String uid;
    private String orderId;

    public abstract int getPriceCents();
    public abstract String getPerson();
    public abstract String getDisplayName();
    public abstract String getDisplayDetails();

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Beverage getBeverage() {
        return beverage;
    }

    public void setBeverage(Beverage beverage) {
        if (!beverage.getName().equals("No Thanks")) {
            this.beverage = beverage;
        }
    }
}
