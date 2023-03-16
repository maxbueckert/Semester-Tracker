package model;

import org.json.JSONObject;
import persistence.Writable;

import java.text.DecimalFormat;

// this class represents a task that the user is tracking
public class Task implements Writable {

    private Course course; // course that task belongs to
    private String name; // name of task
    private double weight; // percent weight of task
    private double dateDouble; // due date of task: numbers to left of decimal = month, numbers to right of decimal=day
                               // e.g., dateDouble = 12.25 represents 12/25 or December 25th
    int taskMonth; // month due date of task
    int taskDay;  // day due date of task
    private double mark; // percent mark received in class


    // REQUIRES: task name length > 1, 0 <= task weight <= 100, 0 < task month <= 12, 0 < task day <= 31
    // EFFECTS:  constructs new task with given name, weight, and due date. task course is set to null.
    //           sets task mark as -1 (this value signals to program that task has no mark)
    public Task(String taskName, double taskWeight, int taskMonth, int taskDay) {
        this.name = taskName;
        this.weight = taskWeight;
        this.taskMonth = taskMonth;
        this.taskDay = taskDay;
        this.dateDouble = storeDateAsDouble(taskMonth, taskDay);
        this.mark = -1;
        this.course = null;
    }

    // REQUIRES: 0 < task month <= 12, 0 < task day <= 31
    // EFFECTS: converts task month and task day integers into a double,
    //          numbers to left of decimal = month, numbers to right of decimal = day
    //          e.g., 12.25 represents 12/25 in mm/dd format
    private double storeDateAsDouble(int taskMonth, int taskDay) {
        double day = (double) taskDay /  100;
        return taskMonth + day;
    }

    // REQUIRES: mark >= 0
    // EFFECTS: returns mark in string format, rounded to 2 decimal places
    public String getMarkRounded() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(this.mark);
    }



    // * This code was adapted from CPSC210 WorkRoomApp
    // EFFECTS: returns task info (name, weight, due date, and mark) as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("weight", weight);
        json.put("taskMonth", taskMonth);
        json.put("taskDay", taskDay);
        json.put("mark", mark);
        return json;
    }


    // getters / setters

    public String getName() {
        return this.name;
    }

    public double getWeight() {
        return this.weight;
    }

    public int getTaskMonth() {
        return this.taskMonth;
    }

    public int getTaskDay() {
        return this.taskDay;
    }

    public double getDateDouble() {
        return this.dateDouble;
    }

    public double getMark() {
        return this.mark;
    }

    public Course getCourse() {
        return this.course;
    }

    // REQUIRES: 0 <= mark <= 100
    public void setMark(double mark) {
        this.mark = mark;
    }

    public void setCourse(Course course) {
        this.course = course;
    }



}
