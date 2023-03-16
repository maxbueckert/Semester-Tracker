package model;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SemesterTest {

    private Semester semester;
    private Course courseOne;
    private Course courseTwo;
    private Course courseThree;
    private Task tOne;
    private Task tTwo;
    private Task tThree;
    private Task tFour;
    private Task tFive;
    private Task tSix;
    private ArrayList<Task> tasks;

    @BeforeEach
    void init() {
        semester = new Semester();
        courseOne = new Course("c1");
        courseTwo = new Course("c2");
        courseThree = new Course("c3");
        tOne = new Task("t1",20, 1, 11);
        tTwo = new Task("t2", 20, 2, 11);
        tThree = new Task("t", 20, 3, 11);
        tFour = new Task("t4", 20, 3, 12);
        tFive = new Task("t5", 20, 3, 13);
        tSix = new Task("t6", 20, 3, 13);
        tasks = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertEquals(0, semester.getCourseList().size());
    }

    @Test
    void testAddCourseSingleCourse() {
        semester.addCourse(courseOne);
        assertEquals(1, semester.getCourseList().size());
        assertEquals(courseOne, semester.getCourseList().get(0));
    }


    @Test
    void testAddCourseMultipleCourses() {
        semester.addCourse(courseOne);
        semester.addCourse(courseTwo);
        semester.addCourse(courseThree);
        assertEquals(3, semester.getCourseList().size());
        assertEquals(courseOne, semester.getCourseList().get(0));
        assertEquals(courseTwo, semester.getCourseList().get(1));
        assertEquals(courseThree, semester.getCourseList().get(2));
    }

    @Test
    void testRemoveCourseSingleCourse() {
        semester.addCourse(courseOne);
        semester.removeCourse(courseOne);
        assertEquals(0, semester.getCourseList().size());
    }

    @Test
    void testRemoveCourseMultipleCourses() {
        semester.addCourse(courseOne);
        semester.addCourse(courseTwo);
        semester.addCourse(courseThree);
        semester.removeCourse(courseOne);
        semester.removeCourse(courseThree);
        assertEquals(1, semester.getCourseList().size());
        assertEquals(courseTwo, semester.getCourseList().get(0));
    }

    @Test
    void testRemoveCourseMiddleOfCourseList() {
        semester.addCourse(courseOne);
        semester.addCourse(courseTwo);
        semester.addCourse(courseThree);
        semester.removeCourse(courseTwo);
        assertEquals(2, semester.getCourseList().size());
        assertEquals(courseOne, semester.getCourseList().get(0));
        assertEquals(courseThree, semester.getCourseList().get(1));
    }

    @Test
    void testNoCoursesEmptyCourseList() {
        assertTrue(semester.noCourses());
    }

    @Test
    void testNoCoursesSingleCourse() {
        semester.addCourse(courseOne);
        assertFalse(semester.noCourses());
    }

    @Test
    void testNoCoursesMultipleCourses() {
        semester.addCourse(courseOne);
        semester.addCourse(courseTwo);
        semester.addCourse(courseThree);
        assertFalse(semester.noCourses());
    }

    @Test
    void testContainsCourseWithNameEmptyCourseList() {
        assertFalse(semester.containsCourseWithName("test"));
    }

    @Test
    void testContainsCourseWithNameInvalidName() {
        semester.addCourse(courseOne);
        semester.addCourse(courseTwo);
        semester.addCourse(courseThree);
        assertFalse(semester.containsCourseWithName("test"));
    }

    @Test
    void testContainsCourseWithNameValidNameSingleCourse() {
        semester.addCourse(courseOne);
        assertTrue(semester.containsCourseWithName("c1"));
    }

    @Test
    void testContainsCourseWithNameValidNameMiddleOfList() {
        semester.addCourse(courseOne);
        semester.addCourse(courseTwo);
        semester.addCourse(courseThree);
        assertTrue(semester.containsCourseWithName("c2"));
    }

    @Test
    void testFindCourseFromNameEmptyCourseList() {
        assertNull(semester.findCourseFromName("test"));
    }

    @Test
    void testFindCourseFromNameInvalidName() {
        semester.addCourse(courseOne);
        semester.addCourse(courseTwo);
        semester.addCourse(courseThree);
        assertNull(semester.findCourseFromName("test"));
    }

    @Test
    void testFindCourseFromNameValidNameSingleCourse() {
        semester.addCourse(courseOne);
        assertEquals(courseOne, semester.findCourseFromName("c1"));
    }

    @Test
    void testFindCourseFromNameValidNameMiddleOfList() {
        semester.addCourse(courseOne);
        semester.addCourse(courseTwo);
        semester.addCourse(courseThree);
        assertEquals(courseTwo, semester.findCourseFromName("c2"));
    }

    @Test
    void testGenerateAllTasksNoCourses() {
        assertEquals(0, semester.generateAllTasks().size());
    }

    @Test
    void testGenerateAllTasksOneCourseNoTasks() {
        semester.addCourse(courseOne);
        assertEquals(0, semester.generateAllTasks().size());
    }

    @Test
    void testGenerateAllTasksOneCourseMultipleTasks() {
        courseOne.addTask(tOne);
        courseOne.addTask(tTwo);
        courseOne.addTask(tThree);
        semester.addCourse(courseOne);
        assertEquals(3, semester.generateAllTasks().size());
        assertEquals(tOne, semester.generateAllTasks().get(0));
        assertEquals(tTwo, semester.generateAllTasks().get(1));
        assertEquals(tThree, semester.generateAllTasks().get(2));

    }

    @Test
    void testGenerateAllTasksMultipleCourseNoTasks() {
        semester.addCourse(courseOne);
        semester.addCourse(courseTwo);
        semester.addCourse(courseThree);
        assertEquals(0, semester.generateAllTasks().size());
    }

    @Test
    void testGenerateAllTasksMultipleCourseMultipleTasks() {
        courseOne.addTask(tOne);
        courseOne.addTask(tTwo);
        courseOne.addTask(tThree);
        courseTwo.addTask(tFour);
        courseTwo.addTask(tFive);
        courseThree.addTask(tSix);
        semester.addCourse(courseOne);
        semester.addCourse(courseTwo);
        semester.addCourse(courseThree);
        assertEquals(6, semester.generateAllTasks().size());
        assertEquals(tOne, semester.generateAllTasks().get(0));
        assertEquals(tTwo, semester.generateAllTasks().get(1));
        assertEquals(tThree, semester.generateAllTasks().get(2));
        assertEquals(tFour, semester.generateAllTasks().get(3));
        assertEquals(tFive, semester.generateAllTasks().get(4));
        assertEquals(tSix, semester.generateAllTasks().get(5));
    }

    @Test
    void testSortAllTasksByDateNoTasks() {
        semester.sortAllTasksByDate(tasks);
        assertEquals(0, tasks.size());
    }

    @Test
    void testSortAllTasksByDateOneTask() {
        tasks.add(tOne);
        semester.sortAllTasksByDate(tasks);
        assertEquals(1, tasks.size());
    }

    @Test
    void testSortAllTasksByDateMultipleTasksAlreadySorted() {
        tasks.add(tOne);
        tasks.add(tTwo);
        tasks.add(tThree);
        semester.sortAllTasksByDate(tasks);
        assertEquals(3, tasks.size());
        assertEquals(tOne, tasks.get(0));
        assertEquals(tTwo, tasks.get(1));
        assertEquals(tThree, tasks.get(2));
    }


    @Test
    void testSortAllTasksByDateMultipleUnsortedTasks(){
        tasks.add(tThree);
        tasks.add(tOne);
        tasks.add(tTwo);
        semester.sortAllTasksByDate(tasks);
        assertEquals(3, tasks.size());
        assertEquals(tOne, tasks.get(0));
        assertEquals(tTwo, tasks.get(1));
        assertEquals(tThree, tasks.get(2));
    }

    @Test
    void testSortAllTasksByDateDifferByOne() {
        tasks.add(tFive);
        tasks.add(tFour);
        semester.sortAllTasksByDate(tasks);
        assertEquals(tFour, tasks.get(0));
        assertEquals(tFive, tasks.get(1));
    }

    @Test
    void testSortAllTasksByDateSameDueDate() {
        tasks.add(tFive);
        tasks.add(tSix);
        semester.sortAllTasksByDate(tasks);
        assertEquals(tFive, tasks.get(0));
        assertEquals(tSix, tasks.get(1));
    }

}
