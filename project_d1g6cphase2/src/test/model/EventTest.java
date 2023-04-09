package model;

import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for the Event class
public class EventTest {
    private Event e;
    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    // * This code was copied from CPSC210 AlarmSystem
    @BeforeEach
    public void runBefore() {
        e = new Event("Course added");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
    }

    // * This code was copied from CPSC210 AlarmSystem
    @Test
    public void testEvent() {
        assertEquals("Course added", e.getDescription());
        assertEquals(d, e.getDate());
    }

    // * This code was copied from CPSC210 AlarmSystem
    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Course added", e.toString());
    }
}
