package pizza.olin.consamables.types;

public class WholePizza extends OrderItem {
    private String uid;
    private HalfPizza leftHalf;
    private HalfPizza rightHalf;

    private int priceCents;

    public WholePizza() { }

    public WholePizza(HalfPizza leftHalf, HalfPizza rightHalf) {
        this.leftHalf = leftHalf;
        this.rightHalf = rightHalf;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
        return priceCents;
    }

    @Override
    public String getDisplayName() {
        return "Whole Pizza";
    }

    @Override
    public String getDisplayDetails() {
        return leftHalf.getDisplayDetails() + " | " + rightHalf.getDisplayDetails();
    }
}
