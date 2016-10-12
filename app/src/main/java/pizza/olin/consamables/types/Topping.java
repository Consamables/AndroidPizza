package pizza.olin.consamables.types;

/**
 * Created by Sam on 10/12/2016.
 */

public class Topping {

    private String uid;
    private String name;

    public Topping() { }

    public Topping(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
