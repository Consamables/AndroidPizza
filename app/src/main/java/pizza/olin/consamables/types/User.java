package pizza.olin.consamables.types;

/**
 * Created by Sam on 10/11/2016.
 */

public class User {

    private String uid;
    private String email;
    private String name;

    public User() { }

    public User(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
