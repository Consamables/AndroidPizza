package pizza.olin.consamables.types;

public class Beverage {
    private String name;
    private int priceCents;

    public Beverage() { }

    public Beverage(String name, int priceCents) {
        this.name = name;
        this.priceCents = priceCents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriceCents() {
        return priceCents;
    }

    public void setPriceCents(int priceCents) {
        this.priceCents = priceCents;
    }

    public String toString() {
        return name;
    }
}
