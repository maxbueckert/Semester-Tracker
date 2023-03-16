package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private Task task;
    private Task taskEdgeCaseBottom;
    private Task taskEdgeCaseTop;


    @BeforeEach
    void init() {
        taskEdgeCaseBottom = new Task("t1", 0.001, 1, 1);
        taskEdgeCaseTop = new Task("t2", 99.99, 12, 31);
        task = new Task("test", 50, 5, 5);
    }

    @Test
    void testConstructorBottomEdgeCase() {
        assertEquals("t1", taskEdgeCaseBottom.getName());
        assertEquals(0.001, taskEdgeCaseBottom.getWeight());
        assertEquals(1, taskEdgeCaseBottom.getTaskMonth());
        assertEquals(1, taskEdgeCaseBottom.getTaskDay());
        assertEquals(-1, taskEdgeCaseBottom.getMark());
        assertNull(taskEdgeCaseBottom.getCourse());
        assertEquals(1.01, taskEdgeCaseBottom.getDateDouble());
    }

    @Test
    void testConstructorTopEdgeCase() {
        assertEquals("t2", taskEdgeCaseTop.getName());
        assertEquals(99.99, taskEdgeCaseTop.getWeight());
        assertEquals(12, taskEdgeCaseTop.getTaskMonth());
        assertEquals(31, taskEdgeCaseTop.getTaskDay());
        assertEquals(-1, taskEdgeCaseTop.getMark());
        assertNull(taskEdgeCaseTop.getCourse());
        assertEquals(12.31, taskEdgeCaseTop.getDateDouble());
    }

    @Test
    void testConstructor() {
        assertEquals("test", task.getName());
        assertEquals(50, task.getWeight());
        assertEquals(5, task.getTaskMonth());
        assertEquals(5, task.getTaskDay());
        assertEquals(-1, task.getMark());
        assertNull(task.getCourse());
        assertEquals(5.05, task.getDateDouble());
    }

    @Test
    void testGetMarkRoundedBottomEdge() {
        task.setMark(0.01);
        assertEquals("0.01", task.getMarkRounded());
    }

    @Test
    void testGetMarkRoundedDecimalPointsAdded() {
        task.setMark(50);
        assertEquals("50.00", task.getMarkRounded());

    }

    @Test
    void testGetMarkRoundedExtraDecimalPointsRounded() {
        task.setMark(50.12999);
        assertEquals("50.13", task.getMarkRounded());
    }

    @Test
    void testGetMarkRounded() {
        task.setMark(50.12);
        assertEquals("50.12", task.getMarkRounded());
    }

    @Test
    void testSetCourse() {
        Course course = new Course("test");
        task.setCourse(course);
        assertEquals(course, task.getCourse());
    }

    @Test
    void testSetMarkEqualsZero() {
        task.setMark(0);
        assertEquals(0, task.getMark());
    }

    @Test
    void testSetMarkEqualsHundred() {
        task.setMark(100);
        assertEquals(100, task.getMark());
    }



}
