package pizza.olin.consamables.types;

public class WholePizza {

    private String uid;
    private HalfPizza leftHalf;
    private HalfPizza rightHalf;

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
}
