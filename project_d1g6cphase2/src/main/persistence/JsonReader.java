package persistence;

import model.Course;
import model.Semester;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads semester from JSON data stored in file
public class JsonReader {
    private String source;

    // * This code was copied from CPSC210 WorkRoomApp
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // * This code was copied from CPSC210 WorkRoomApp
    // EFFECTS: reads semester from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Semester read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSemester(jsonObject);
    }

    // * This code was copied from CPSC210 WorkRoomApp
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // * This code was adapted from CPSC210 WorkRoomApp
    // EFFECTS: parses semester from JSON object and returns it
    private Semester parseSemester(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("courseList");
        Semester semester = new Semester();
        for (Object json : jsonArray) {
            JSONObject nextCourse = (JSONObject) json;
            Course course = parseCourse(nextCourse);
            semester.addCourse(course);

        }
        return semester;
    }

    // EFFECTS: parses course from JSON object and returns it
    private Course parseCourse(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Course course = new Course(name);
        JSONArray jsonArray = jsonObject.getJSONArray("taskList");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            Task task = parseTask(nextTask);
            course.addTask(task);
        }
        return course;
    }

    // EFFECTS: parses task from JSON object and returns it
    private Task parseTask(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double weight = jsonObject.getDouble("weight");
        int taskMonth = jsonObject.getInt("taskMonth");
        int taskDay = jsonObject.getInt("taskDay");
        double mark = jsonObject.getDouble("mark");

        Task task = new Task(name, weight, taskMonth, taskDay);
        task.setMark(mark);

        return task;
    }

}
