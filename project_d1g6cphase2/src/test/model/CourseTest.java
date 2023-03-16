package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;


class CourseTest {

    private Course course;
    private Task taskOne;
    private Task taskTwo;
    private Task taskThree;
    private Task taskFour;


    @BeforeEach
    void init() {
        course = new Course("test");
        taskOne = new Task("t1", 25, 8, 1);
        taskTwo = new Task("t2", 25, 8, 2);
        taskThree = new Task("t3", 50, 10, 1);
        taskFour = new Task("t4", 50, 10, 1);

    }

    @Test
    void testConstructor() {
        assertEquals(0, course.getTaskList().size());
        assertEquals("test", course.getName());
    }

    @Test
    void testAddTaskOnce() {
        course.addTask(taskOne);
        assertEquals(1, course.getTaskList().size());
        assertEquals(taskOne, course.getTaskList().get(0));
        assertEquals(course, taskOne.getCourse());

    }

    @Test
    void testAddTaskMultipleTasksInOrderDifferByOne() {
        course.addTask(taskOne);
        course.addTask(taskTwo);
        course.addTask(taskThree);
        assertEquals(3, course.getTaskList().size());
        assertEquals(taskOne, course.getTaskList().get(0));
        assertEquals(taskTwo, course.getTaskList().get(1));
        assertEquals(taskThree, course.getTaskList().get(2));
        assertEquals(course, taskOne.getCourse());
        assertEquals(course, taskTwo.getCourse());
        assertEquals(course, taskThree.getCourse());
    }

    @Test
    void testAddTaskMultipleTasksOutOfOrderDifferByOne() {
        course.addTask(taskThree);
        course.addTask(taskOne);
        course.addTask(taskTwo);
        assertEquals(3, course.getTaskList().size());
        assertEquals(taskOne, course.getTaskList().get(0));
        assertEquals(taskTwo, course.getTaskList().get(1));
        assertEquals(taskThree, course.getTaskList().get(2));
        assertEquals(course, taskOne.getCourse());
        assertEquals(course, taskTwo.getCourse());
        assertEquals(course, taskThree.getCourse());

    }

    @Test
    void testAddMultipleTasksSameDueDate() {
        course.addTask(taskThree);
        course.addTask(taskFour);
        assertEquals(taskThree, course.getTaskList().get(0));
        assertEquals(taskFour, course.getTaskList().get(1));
    }

    @Test
    void testAddMultipleTasksSameDueDateReverseOrder() {
        course.addTask(taskFour);
        course.addTask(taskThree);
        assertEquals(taskThree, course.getTaskList().get(1));
        assertEquals(taskFour, course.getTaskList().get(0));
    }

    @Test
    void testGetAverageNoMarksAdded() {
        course.addTask(taskOne);
        course.addTask(taskTwo);
        course.addTask(taskThree);
        assertEquals("n/a", course.getAverage());
    }

    @Test
    void testGetAverageRounded() {
        course.addTask(taskOne);
        course.addTask(taskTwo);
        course.addTask(taskThree);
        taskOne.setMark(65.5999999);
        assertEquals("65.60%", course.getAverage());
    }

    @Test
    void testGetAverageOneMark() {
        course.addTask(taskOne);
        course.addTask(taskTwo);
        course.addTask(taskThree);
        taskOne.setMark(65.59);
        assertEquals("65.59%", course.getAverage());
    }

    @Test
    void testGetAverageFirstTwoTasksMarked() {
        course.addTask(taskOne);
        course.addTask(taskTwo);
        course.addTask(taskThree);
        taskOne.setMark(10.00);
        taskTwo.setMark(30.00);
        assertEquals("20.00%", course.getAverage());
    }

    @Test
    void testGetAverageMiddleTaskNotMarked() {
        course.addTask(taskOne);
        course.addTask(taskTwo);
        course.addTask(taskThree);
        taskOne.setMark(10.00);
        taskThree.setMark(100.00);
        assertEquals("70.00%", course.getAverage());
    }

    @Test
    void testGetAverageAllMarked() {
        course.addTask(taskOne);
        course.addTask(taskTwo);
        course.addTask(taskThree);
        taskOne.setMark(10.00);
        taskTwo.setMark(30.00);
        taskThree.setMark(90.00);
        assertEquals("55.00%", course.getAverage());
    }

    @Test
    void testRemoveTaskSingle() {
        course.addTask(taskOne);
        course.removeTask(taskOne);
        assertEquals(0, course.getTaskList().size());
        assertNull(taskOne.getCourse());
    }

    @Test
    void testRemoveTaskMiddleOfList() {
        course.addTask(taskOne);
        course.addTask(taskTwo);
        course.addTask(taskThree);
        course.removeTask(taskTwo);
        assertEquals(2, course.getTaskList().size());
        assertEquals(taskOne, course.getTaskList().get(0));
        assertEquals(taskThree, course.getTaskList().get(1));
        assertNull(taskTwo.getCourse());
    }

    @Test
    void testRemoveTaskEndOfList() {
        course.addTask(taskOne);
        course.addTask(taskTwo);
        course.addTask(taskThree);
        course.removeTask(taskThree);
        assertEquals(2, course.getTaskList().size());
        assertEquals(taskOne, course.getTaskList().get(0));
        assertEquals(taskTwo, course.getTaskList().get(1));
        assertNull(taskThree.getCourse());
    }

    @Test
    void testRemoveTaskMultiple() {
        course.addTask(taskOne);
        course.addTask(taskTwo);
        course.addTask(taskThree);
        course.removeTask(taskOne);
        assertEquals(2, course.getTaskList().size());
        assertEquals(taskTwo, course.getTaskList().get(0));
        assertEquals(taskThree, course.getTaskList().get(1));
        assertNull(taskOne.getCourse());
        course.removeTask(taskThree);
        assertEquals(1, course.getTaskList().size());
        assertEquals(taskTwo, course.getTaskList().get(0));
        assertNull(taskThree.getCourse());
    }

    @Test
    void testContainsTaskWithNameEmptyTaskList() {
        assertFalse(course.containsTaskWithName("test"));
    }

    @Test
    void testContainsTaskWithNameValidNameSingleList() {
        course.addTask(taskOne);
        assertTrue(course.containsTaskWithName("t1"));
    }


    @Test
    void testContainsTaskWithNameInvalidNameSingleList() {
        course.addTask(taskOne);
        assertFalse(course.containsTaskWithName("t2"));
    }


    @Test
    void testContainsTaskWithNameValidNameLongTaskList() {
        course.addTask(taskOne);
        course.addTask(taskTwo);
        course.addTask(taskThree);
        assertTrue(course.containsTaskWithName("t3"));
    }

    @Test
    void testContainsTaskWithNameInvalidNameLongTaskList() {
        course.addTask(taskOne);
        course.addTask(taskTwo);
        course.addTask(taskThree);
        assertFalse(course.containsTaskWithName("t4"));
    }




    @Test
    void testFindTaskFromNameEmptyTaskList() {
        assertNull(course.findTaskFromName("test"));
    }

    @Test
    void testFindTaskFromNameValidTaskSingleList() {
        course.addTask(taskOne);
        assertEquals(taskOne, course.findTaskFromName("t1"));
    }


    @Test
    void testFindTaskFromNameInvalidNameSingleList() {
        course.addTask(taskOne);
        assertNull(course.findTaskFromName("t2"));
    }


    @Test
    void testFindTaskFromNameValidNameLongTaskList() {
        course.addTask(taskOne);
        course.addTask(taskTwo);
        course.addTask(taskThree);
        assertEquals(taskThree, course.findTaskFromName("t3"));
    }

    @Test
    void testFindTaskFromNameInvalidNameLongTaskList() {
        course.addTask(taskOne);
        course.addTask(taskTwo);
        course.addTask(taskThree);
        assertNull(course.findTaskFromName("t4"));
    }


    @Test
    void testHasTasksNoTasks() {
        assertFalse(course.hasTasks());
    }

    @Test
    void testHasTasksOneTask() {
        course.addTask(taskOne);
        assertTrue(course.hasTasks());
    }

    @Test
    void testHasTasksMultipleTasks() {
        course.addTask(taskOne);
        course.addTask(taskTwo);
        course.addTask(taskThree);
        assertTrue(course.hasTasks());
    }


}