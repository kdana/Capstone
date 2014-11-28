import java.util.Date; // Deprecated, use java.util.Calendar;
import java.awt.Point;

public class Meeting {
    private String name;
    private Date start_date;
    private Date end_date;
    private Point location;
    private String description;

    public Meeting(String name, Date start_date, Date end_date, 
                   Point location, String description) {
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.location = location;
        this.description = description;
    }

    public Meeting(String name, Date start_date, Date end_date, Point location) {
        this(name, start_date, end_date, location, null);
    }

    public void inviteCirlce() {
    
    }

    public String getName() {
        return name;
    }

    public Date getStart() {
        return start_date;
    }

    public Date getEnd() {
        return end_date;
    }

    public Point getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart(Date start_date) {
        this.start_date = start_date;
    }

    public void setEnd(Date end_date) {
        this.end_date = end_date;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
