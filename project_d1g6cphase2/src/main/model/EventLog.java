package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// * This code was copied from CPSC210 AlarmSystem
// Represents a log of add/remove course events
// We use the Singleton Design Pattern to ensure that there is only
// one EventLog in the system and that the system has global access
// to the single instance of the EventLog.

public class EventLog implements Iterable<Event> {
    // the only EventLog in the system (Singleton Design Pattern)
    private static EventLog theLog;
    private Collection<Event> events;

    // * This code was copied from CPSC210 AlarmSystem
    // EFFECTS: Creates EventLog with empty log of events
    // Note to Dev: Private status prevents external construction (Singleton Design Pattern).
    private EventLog() {
        events = new ArrayList<Event>();
    }

    // * This code was copied from CPSC210 AlarmSystem
    //EFFECTS: Gets instance of EventLog - creates it if it doesn't already exist (Singleton Design Pattern)
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    // * This code was copied from CPSC210 AlarmSystem
    // MODIFIES: this
    // EFFECTS: Adds an event to the event log.
    public void logEvent(Event e) {
        events.add(e);
    }

    // * This code was copied from CPSC210 AlarmSystem
    // MODIFIES: this
    // EFFECTS: Clears the event log and logs the event.
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    // * This code was copied from CPSC210 AlarmSystem
    // EFFECTS: Returns iterable of collection of events in this
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
