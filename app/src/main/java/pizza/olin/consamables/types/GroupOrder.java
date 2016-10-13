package pizza.olin.consamables.types;

import java.util.Date;

public class GroupOrder {

    private String uid;
    private Date startTime;
    private int durationMinutes;
    public boolean isClosed; // firebase pls

    public GroupOrder() {
    }

    public GroupOrder(String uid, Date startTime, int durationMinutes, boolean isClosed) {
        this.uid = uid;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.isClosed = isClosed;
    }

    public GroupOrder(int durationMinutes) {
        startTime = new Date();
        this.durationMinutes = durationMinutes;
        isClosed = false;
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
