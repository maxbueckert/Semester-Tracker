package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.text.DecimalFormat;



// This class represents a course that the user is enrolled in during the semester
public class Course implements Writable {

    private String name; // course name
    private List<Task> taskList; // list of tasks in the course

    // REQUIRES: Course name length > 1, course name does not begin or end with " "
    // EFFECTS: Creates a new course with given name and an empty task list
    public Course(String name) {
        this.name = name;
        taskList = new ArrayList<>();
    }

    // REQUIRES: Task list does not already contain a task with same task name
    // MODIFIES: This, Task
    // EFFECTS: adds task to course task list, and re-sorts the task list so that all tasks are
    //           listed in chronological order based on due date; also sets the given task's course to this course
    public void addTask(Task task) {
        taskList.add(task);
        task.setCourse(this);
        sortTasksByDate();
    }

    // MODIFIES: this
    // EFFECTS: sorts the task list such that all tasks are listed in chronological order based on due date
    //          if two tasks have same due date, then their current relative order is maintained
    private void sortTasksByDate() {
        Collections.sort(taskList, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return Double.compare(t1.getDateDouble(), t2.getDateDouble());
            }
        });
    }

    // REQUIRES: Combined task weight of all tasks in task list <= 100
    // EFFECTS: If there is at least 1 graded task in task list, then returns the weighted percent average
    //          of all graded tasks to 2 decimal places in string format
    //          If there is no graded task in task list, then returns n/a in string format
    public String getAverage() {
        double sumMarks = 0;
        int numMarks = 0;
        double totalWeight = 0;

        for (Task task : taskList) {
            if (task.getMark() >= 0) {
                totalWeight += task.getWeight();
            }
        }
        for (Task task : taskList) {
            if (task.getMark() >= 0) {
                double weightedMark = task.getMark() * task.getWeight() / totalWeight;
                sumMarks += weightedMark;
                numMarks++;
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        if (numMarks > 0) {
            return df.format(sumMarks) + "%";
        } else {
            return "n/a";
        }
    }

    // REQUIRES: task is in task list
    // MODIFIES: this, task
    // EFFECTS: removes task from course task list, and sets given task's course to null
    public void removeTask(Task task) {
        task.setCourse(null);
        taskList.remove(task);
    }

    // EFFECTS: returns true if task with given name is in course task list
    //          returns false if task with given name is not in course task list
    public boolean containsTaskWithName(String name) {
        for (Task task : taskList) {
            if (name.equals(task.getName())) {
                return true;
            }
        }
        return false;
    }


    // EFFECTS: returns the task with the given name in task list, or null if task does not exist
    public Task findTaskFromName(String name) {
        for (Task task : taskList) {
            if (task.getName().equals(name)) {
                return task;
            }
        }
        return null;
    }

    // EFFECTS: returns true is task list is not empty, false if task list is empty
    public boolean hasTasks() {
        return taskList.size() > 0;
    }




    // * This code was adapted from CPSC210 WorkRoomApp
    // EFFECTS: returns course info (name and task list) as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("taskList", taskListToJson());
        return json;
    }


    // * This code was adapted from CPSC210 WorkRoomApp
    // EFFECTS: returns course task list as JSON array
    public JSONArray taskListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task task : taskList) {
            jsonArray.put(task.toJson());
        }
        return jsonArray;
    }

    // getters and setters

    public String getName() {
        return this.name;
    }

    public List<Task> getTaskList() {
        return this.taskList;
    }



}
