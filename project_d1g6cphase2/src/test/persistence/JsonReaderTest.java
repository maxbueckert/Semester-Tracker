package persistence;

import model.Task;
import model.Course;
import model.Semester;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    // * This code was adapted from CPSC210 WorkRoomApp
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Semester sem = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    // * This code was adapted from CPSC210 WorkRoomApp
    @Test
    void testReaderEmptySemester() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySemester.json");
        try {
            Semester sem = reader.read();
            assertEquals(0, sem.getCourseList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    // * This code was adapted from CPSC210 WorkRoomApp
    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSemester.json");
        try {
            Semester sem = reader.read();
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
            assertEquals(25,  cpscTaskList.get(1).getTaskDay());
            assertEquals(25,  cpscTaskList.get(1).getWeight());
            assertEquals(-1,  cpscTaskList.get(1).getMark());


            assertEquals(2, germanTaskList.size());
            assertEquals("oral quiz 1", germanTaskList.get(0).getName());
            assertEquals(1,  germanTaskList.get(0).getTaskMonth());
            assertEquals(23,  germanTaskList.get(0).getTaskDay());
            assertEquals(10,  germanTaskList.get(0).getWeight());
            assertEquals(91.34,  germanTaskList.get(0).getMark());

            assertEquals("listening quiz 1", germanTaskList.get(1).getName());
            assertEquals(1,  germanTaskList.get(1).getTaskMonth());
            assertEquals(31,  germanTaskList.get(1).getTaskDay());
            assertEquals(30,  germanTaskList.get(1).getWeight());
            assertEquals(85.03,  germanTaskList.get(1).getMark());


            assertEquals(2, mathTaskList.size());
            assertEquals("midterm", mathTaskList.get(0).getName());
            assertEquals(2,  mathTaskList.get(0).getTaskMonth());
            assertEquals(16,  mathTaskList.get(0).getTaskDay());
            assertEquals(40,  mathTaskList.get(0).getWeight());
            assertEquals(89.11,  mathTaskList.get(0).getMark());

            assertEquals("final", mathTaskList.get(1).getName());
            assertEquals(4,  mathTaskList.get(1).getTaskMonth());
            assertEquals(29,  mathTaskList.get(1).getTaskDay());
            assertEquals(60,  mathTaskList.get(1).getWeight());
            assertEquals(-1,  mathTaskList.get(1).getMark());



        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}