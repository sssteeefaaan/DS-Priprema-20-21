package Januar2019;

import java.io.Serializable;

public class Reservation implements Serializable {
    public int id;
    public int day;
    public int month;
    public int hour;
    public int numHours;

    public Reservation(int id, int day, int month, int hour, int numHours) {
        super();

        this.id = id;
        this.day = day;
        this.month = month;
        this.hour = hour;
        this.numHours = numHours;
    }
}
