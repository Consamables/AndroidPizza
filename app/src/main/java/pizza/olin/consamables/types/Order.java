package pizza.olin.consamables.types;

import java.util.Date;

/**
 * Created by Sam on 10/11/2016.
 */

public class Order {

    private String uid;
    private Date startTime;
    private int duration;

    public Order() { }

    public Order(int duration) {
        this.startTime = new Date();
        this.duration = duration;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
