package persistence;

import model.Task;
import model.Course;
import model.Semester;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    // * This code was adapted from CPSC210 WorkRoomApp
    @Test
    void testWriterInvalidFile() {
        try {
            Semester sem = new Semester();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    // * This code was adapted from CPSC210 WorkRoomApp
    @Test
    void testWriterEmptyWorkroom() {
        try {
            Semester sem = new Semester();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySemester.json");
            writer.open();
            writer.write(sem);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySemester.json");
            sem = reader.read();
            assertEquals(0, sem.getCourseList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // * This code was adapted from CPSC210 WorkRoomApp
    @Test
    void testWriterGeneralWorkroom() {
        try {
            Semester sem = new Semester();
            Course cpscCourse = new Course("cpsc450");
            Course germanCourse = new Course("german275");
            Course mathCourse = new Course("math376");

            Task cpscMidtermOne = new Task("midterm 1", 25, 1, 25);
            cpscMidtermOne.setMark(76.851);
            Task cpscMidtermTwo = new Task("midterm 2", 30, 2, 23);
            cpscCourse.addTask(cpscMidtermOne);
            cpscCourse.addTask(cpscMidtermTwo);


            Task germanTestOne = new Task("oral quiz 1", 25, 1, 25);
            germanTestOne.setMark(91.34);
            Task cpscGermanTestTwo = new Task("listening quiz 1", 10, 1, 31);
            cpscGermanTestTwo.setMark(85.03);
            germanCourse.addTask(cpscGermanTestTwo);
            germanCourse.addTask(germanTestOne);

            sem.addCourse(cpscCourse);
            sem.addCourse(germanCourse);
            sem.addCourse(mathCourse);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(sem);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            sem = reader.read();

            assertEquals(3, sem.getCourseList().size());
            Course cpsc = sem.getCourseList().get(0);
            Course german = sem.getCourseList().get(1);
            Course math = sem.getCourseList().get(2);

            assertEquals("cpsc450", cpsc.getName());
            assertEquals("german275", german.getName());
            assertEquals("math376", math.getName());

            List<Task> cpscTaskList = cpsc.getTaskList();
            List<Task> germanTaskList = german.getTaskList();
            List<Task> mathTaskList = math.getTaskList();

            assertEquals(2, cpscTaskList.size());
            assertEquals("midterm 1", cpscTaskList.get(0).getName());
            assertEquals(1,  cpscTaskList.get(0).getTaskMonth());
            assertEquals(25,  cpscTaskList.get(0).getTaskDay());
            assertEquals(25,  cpscTaskList.get(0).getWeight());
            assertEquals(76.851,  cpscTaskList.get(0).getMark());

            assertEquals("midterm 2", cpscTaskList.get(1).getName());
            assertEquals(2,  cpscTaskList.get(1).getTaskMonth());
            assertEquals(23,  cpscTaskList.get(1).getTaskDay());
            assertEquals(30,  cpscTaskList.get(1).getWeight());
            assertEquals(-1,  cpscTaskList.get(1).getMark());


            assertEquals(2, germanTaskList.size());
            assertEquals("oral quiz 1", germanTaskList.get(0).getName());
            assertEquals(1,  germanTaskList.get(0).getTaskMonth());
            assertEquals(25,  germanTaskList.get(0).getTaskDay());
            assertEquals(25,  germanTaskList.get(0).getWeight());
            assertEquals(91.34,  germanTaskList.get(0).getMark());

            assertEquals("listening quiz 1", germanTaskList.get(1).getName());
            assertEquals(1,  germanTaskList.get(1).getTaskMonth());
            assertEquals(31,  germanTaskList.get(1).getTaskDay());
            assertEquals(10,  germanTaskList.get(1).getWeight());
            assertEquals(85.03,  germanTaskList.get(1).getMark());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}