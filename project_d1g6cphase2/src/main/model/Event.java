package model;

import java.util.Calendar;
import java.util.Date;


// * This code was copied from CPSC210 AlarmSystem
// Represents an add or remove course event.
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;


    // This code was copied from CPSC210 AlarmSystem App
    //  EFFECTS: Creates an event with the given description
    //  and the current date/time stamp.
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    // This code was copied from CPSC210 AlarmSystem App
    //  EFFECTS: Returns the date of this event (includes time).
    public Date getDate() {
        return dateLogged;
    }


    // This code was copied from CPSC210 AlarmSystem App
    //  EFFECTS: Returns the description of this event.
    public String getDescription() {
        return description;
    }

    // This code was copied from CPSC210 AlarmSystem App
    // EFFECTS: Compares given object with this and determines if they are equal (true = yes, false = no)
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Event otherEvent = (Event) other;
        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    // This code was copied from CPSC210 AlarmSystem App
    // EFFECTS: Generates and returns hashcode for this event
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    // This code was copied from CPSC210 AlarmSystem App
    // EFFECTS: Generates and returns string representation of this event
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}
