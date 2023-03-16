package model;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// this class represents the semester that the user is tracking with the app
public class Semester implements Writable {

    private List<Course> courseList; // courses that user is enrolled in for tracked semester

    // EFFECTS: constructs a new semester with an empty list of courses
    public Semester() {
        this.courseList = new ArrayList<>();
    }


    // REQUIRES: course with given name is not already in course list
    // MODIFIES: this
    // EFFECTS: adds given course to semester's course list
    public void addCourse(Course course) {
        courseList.add(course);
    }


    // REQUIRES: course is in course list
    // MODIFIES: this
    // EFFECTS: removes given course from semester course list
    public void removeCourse(Course courseToRemove) {
        courseList.remove(courseToRemove);
    }

    // EFFECTS: returns true if course list is empty, otherwise returns false
    public boolean noCourses() {
        return courseList.size() == 0;
    }

    // EFFECTS: returns true if course with given name is in course list, otherwise returns false
    public boolean containsCourseWithName(String name) {
        for (Course course : courseList) {
            if (name.equals(course.getName())) {
                return true;
            }
        }
        return false;
    }


    // EFFECTS: returns course from course list with given name, or returns null
    public Course findCourseFromName(String name) {
        for (Course course : courseList) {
            if (name.equals(course.getName())) {
                return course;
            }
        }
        return null;
    }

    // EFFECTS: returns list of all tasks from all courses in course list
    public ArrayList<Task> generateAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (Course course: courseList) {
            for (Task task : course.getTaskList()) {
                allTasks.add(task);
            }
        }
        return allTasks;
    }

    // EFFECTS: returns sorted list of tasks, sorted chronologically based on task due date
    //          if two tasks have same due date, then their current relative order is maintained
    public ArrayList<Task> sortAllTasksByDate(ArrayList<Task> allTasks) {
        Collections.sort(allTasks, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return Double.compare(t1.getDateDouble(), t2.getDateDouble());
            }
        });
        return allTasks;
    }



    // * This code was adapted from CPSC210 WorkRoomApp
    // EFFECTS: returns semester info (course list) as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("courseList", courseListToJson());
        return json;
    }

    // * This code was adapted from CPSC210 WorkRoomApp
    // EFFECTS: returns course info (name and task list) as JSON object
    private JSONArray courseListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Course course : courseList) {
            jsonArray.put(course.toJson());
        }

        return jsonArray;
    }


    // getters

    public List<Course> getCourseList() {
        return courseList;
    }




}




