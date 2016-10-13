package pizza.olin.consamables.types;

import java.util.Date;

public class GroupOrder {

    private String uid;
    private Date startTime;
    private int durationMinutes;

    public GroupOrder() { }

    public GroupOrder(int durationMinutes) {
        this.startTime = new Date();
        this.durationMinutes = durationMinutes;
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

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}
