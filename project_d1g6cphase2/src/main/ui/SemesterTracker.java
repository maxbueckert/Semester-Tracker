package ui;

import model.Semester;
import model.Course;
import model.Task;

import java.awt.event.ActionEvent;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.FileNotFoundException;
import java.io.IOException;


import model.EventLog;
import model.Event;

import javax.swing.*;


// Represents Semester Tracker application
public class SemesterTracker extends JFrame {
    private static final String JSON_STORE = "./data/semester.json";
    protected static Semester semester;

    private Scanner input;
    private String command;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: Prints welcome statement, initializes SemesterTracker program, and runs intro interface
    public SemesterTracker() {
        init();
        new SemesterTrackerGUI();
        System.out.println("\nWelcome to SemesterTracker!");
        runIntroUI();
    }



    // EFFECTS: Constructor called from SemesterTrackerGUI, re-initializes SemesterTracker programs
    protected SemesterTracker(String dummy) {
        init();
    }



    // MODIFIES: this
    // EFFECTS: initializes program: creates new semester, adds the pre-installed courses to semester
    private void init() {
        input = new Scanner(System.in);
        semester = new Semester();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
//        initCpsc();
//        initGerman();
//        initMath();
    }

//    // MODIFIES: this
//    // EFFECTS: constructs example math course and adds it to semester
//    private void initMath() {
//        Course math = new Course("math376");
//        Task midterm = new Task("midterm", 40, 2, 16);
//        midterm.setMark(89.11);
//        math.addTask(midterm);
//        math.addTask(new Task("final", 60, 4, 29));
//        semester.addCourse(math);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: constructs example german course and adds it to semester
//    private void initGerman() {
//        Course german = new Course("german275");
//        Task oralOne = new Task("oral quiz 1", 10, 1, 23);
//        oralOne.setMark(91.34);
//        german.addTask(oralOne);
//        Task listeningOne = new Task("listening quiz 1", 30, 1, 31);
//        listeningOne.setMark(85.03);
//        for (int i = 2; i < 12; i++) {
//            german.addTask(new Task("oral quiz " + i, 20, i, 23));
//            german.addTask(new Task("listening quiz " + i, 20, i, 31));
//            german.addTask(new Task("exam " + 1, 20, i, 27));
//
//        }
//        semester.addCourse(german);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: constructs example cpsc course and adds it to semester
//    private void initCpsc() {
//        Course cpcs = new Course("cpsc450");
//        Task midtermOne = new Task("midterm 1", 25, 1, 25);
//        midtermOne.setMark(96.851);
//        cpcs.addTask(midtermOne);
//        cpcs.addTask(new Task("midterm 2", 25, 2, 25));
//        cpcs.addTask(new Task("final", 50, 4, 25));
//        semester.addCourse(cpcs);
//    }

    // EFFECTS: prints goodbye statement then exits program
    protected void exitProgram() {
        System.out.println("Goodbye!");
        EventLog el = EventLog.getInstance();
        System.out.println("Event Log: ");
        for (Event next : el) {
            System.out.println(next.toString());
        }
        System.exit(0);
    }


    // MODIFIES: this
    // EFFECTS: records the next line of user input in all lower-cases
    private void initCommand() {
        command = input.nextLine();
        command = command.toLowerCase();
    }





    // ----------INTRO -------------


    // MODIFIES: This
    // EFFECTS: constructs the Main Menu console user interface
    private void runIntroUI() {
        displayIntroMenu();
        initCommand();
        processIntroCommand();
    }

    // EFFECTS: prints main-menu options to console
    private void displayIntroMenu() {
        System.out.println("\nPlease select from the following options:");
        System.out.println("\tc -> view your courses");
        System.out.println("\tt -> view your upcoming tasks");
        System.out.println(("\ts -> save your semester"));
        System.out.println(("\tl -> load your previous semester"));
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command: redirects user to desired program location based on user
    //          input or notifies user that their input was invalid
    private void processIntroCommand() {
        if (command.equals("c")) {
            runCoursesUI();
        } else if (command.equals("t")) {
            runAllTasksUI();
        } else if (command.equals("q")) {
            exitProgram();
        } else if (command.equals("s")) {
            saveSemester();
        } else if (command.equals("l")) {
            loadSemester();
        } else {
            invalidIntroInput();
        }
    }


    // * This code was adapted from CPSC210 WorkRoomApp
    // EFFECTS: saves semester to file
    protected boolean saveSemester() {
        try {
            jsonWriter.open();
            jsonWriter.write(semester);
            jsonWriter.close();
            System.out.println("Saved semester to " + JSON_STORE);
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
            return false;
        }
    }

    // * This code was adapted from CPSC210 WorkRoomApp
    // MODIFIES: this
    // EFFECTS: loads semester from file
    protected boolean loadSemester() {
        try {
            semester = jsonReader.read();
            System.out.println("Loaded semester from " + JSON_STORE);
            return true;
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            return false;
        }
    }


    // MODIFIES: this
    // EFFECTS: notifies user that input was invalid, the next user input will be re-processed
    private void invalidIntroInput() {
        System.out.println("Please select a valid input");
        initCommand();
        processIntroCommand();
    }








    // ------------ ALLTASKS CONSOLE -------------


    // MODIFIES: This
    // EFFECTS: constructs the All Tasks console user interface
    private void runAllTasksUI() {
        displayAllTasksMenu();
        initCommand();
        processAllTasksCommand();
    }

    // EFFECTS: prints all the semester's tasks, and user options
    private void displayAllTasksMenu() {
        ArrayList<Task> allTasks = semester.sortAllTasksByDate(semester.generateAllTasks());
        if (allTasks.size() == 0) {
            System.out.println("\nYou have no upcoming tasks");
        } else {
            System.out.println("\nAll Tasks: ");
            for (Task task : allTasks) {
                printTaskForSemester(task);
            }
        }
        System.out.println("\tm -> view Main Menu");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command: redirects user to desired program location based on user
    //          input or notifies user that their input was invalid
    private void processAllTasksCommand() {
        if (command.equals("m")) {
            runIntroUI();
        } else if (command.equals("q")) {
            exitProgram();
        } else {
            invalidAllTasksInput();
        }
    }

    // MODIFIES: this
    // EFFECTS: notifies user that input was invalid, the next user input will be re-processed
    private void invalidAllTasksInput() {
        System.out.println("Please select a valid input");
        initCommand();
        processAllTasksCommand();
    }

    // EFFECTS: prints relative information (due date, name, weight, mark, course) about single task
    private void printTaskForSemester(Task task) {
        String taskName = task.getName();
        int taskMonth = task.getTaskMonth();
        int taskDay = task.getTaskDay();
        double weight = task.getWeight();
        double mark = task.getMark();
        String markRounded = task.getMarkRounded();
        String course = task.getCourse().getName();
        String firstOutput = "\t " + taskMonth + "/" + taskDay + ": \t ";
        firstOutput += taskName + "," + "\t Weight: " + weight + "%,";
        System.out.print(firstOutput);
        if (mark < 0) {
            System.out.print("\t Mark: n/a,");
        } else {
            System.out.print("\t Mark: " + markRounded + "%,");
        }
        System.out.println("\t Course: " + course);
    }








    // ----------  ALL COURSES CONSOLE ----------------

    // MODIFIES: this
    // EFFECTS: constructs the All Courses user interface
    private void runCoursesUI() {
        displayCoursesMenu();
        initCommand();
        processCoursesCommand();
    }

    // EFFECTS: prints all the semester's courses to console, and user options
    private void displayCoursesMenu() {
        if (!semester.noCourses()) {
            System.out.println("\nEnter the name of the course you would to inspect, or select from other options.");
            System.out.println("Course List:");
            List<Course> courseList = semester.getCourseList();
            for (int i = 0; i < courseList.size(); i++) {
                String courseName = courseList.get(i).getName();
                System.out.println("\t  -> " + courseName);
            }
        } else {
            System.out.println("\nYou are currently enrolled in no courses");
        }
        System.out.println("Other Options:");
        System.out.println("\tc -> add Course");
        System.out.println("\tm -> view Main Menu");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command: redirects user to desired program location based on user
    //          input or notifies user that their input was invalid
    private void processCoursesCommand() {
        if (command.equals("m")) {
            runIntroUI();
        } else if (command.equals("c")) {
            runAddCourseUI();
        }  else if (command.equals("q")) {
            exitProgram();
        } else if (semester.containsCourseWithName(command)) {
            runSingleCourseUI(semester.findCourseFromName(command));
        } else {
            invalidCoursesInput();
        }
    }

    // MODIFIES: this
    // EFFECTS: notifies user that input was invalid, the next user input will be re-processed
    private void invalidCoursesInput() {
        System.out.println("Please select a valid input");
        initCommand();
        processCoursesCommand();
    }








    // ---------- ADD COURSE ------------

    // MODIFIES: this
    // EFFECTS: constructs the Add Course user interface
    private void runAddCourseUI() {
        displayAddCourseMenu();
        invalidCourseName();
    }

    // EFFECTS: prints the add-course instructions
    private void displayAddCourseMenu() {
        System.out.println("\nPlease enter the course name, or press b to cancel");
    }

    // MODIFIES: this
    // EFFECTS: processes user command: adds course, the name of which is set to the user input, unless the name
    //          is invalid, or user cancels course addition.
    private void processAddCourseCommand() {
        if (command.equals("b")) {
            runCoursesUI();
        } else if (command.length() <= 1) {
            System.out.println("Course name must contain more than 1 character");
            invalidCourseName();
        } else if (command.substring(0, 1).equals(" ")) {
            System.out.println("Course name cannot start with a space");
            invalidCourseName();
        } else if (command.substring(command.length() - 1).equals(" ")) {
            System.out.println("Course name cannot end with a space");
            invalidCourseName();
        } else if (semester.containsCourseWithName(command)) {
            System.out.println("Course with name already exists in semester");
            invalidCourseName();
        } else {
            Course courseToAdd = new Course(command);
            semester.addCourse(courseToAdd);
            verifyAddedCourse(command);
            runFurtherOptionsAddCourseUI(courseToAdd);
        }
    }

    // MODIFIES: this
    // EFFECTS: notifies user that input was invalid, the next user input will be re-processed
    private void invalidCourseName() {
        initCommand();
        processAddCourseCommand();
    }

    // EFFECTS: prints course-addition verification
    private void verifyAddedCourse(String command) {
        System.out.println("The following course has been added to your semester: " + command);
    }


    // MODIFIES: this
    // EFFECTS: constructs the next-options user interface, after user has added a course
    private void runFurtherOptionsAddCourseUI(Course course) {
        displayFurtherOptionsAddCourse(course);
        initCommand();
        processFurtherOptionsAddCourse(course);
    }

    // EFFECTS: prints the next-options options
    private void displayFurtherOptionsAddCourse(Course course) {
        System.out.println("\t e -> inspect " + course.getName());
        System.out.println("\t a -> add another course");
        System.out.println("\t m -> main menu");
        System.out.println("\t q -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command: redirects user to desired program location based on user
    //          input or notifies user that their input was invalid
    private void processFurtherOptionsAddCourse(Course course) {
        if (command.equals("e")) {
            runSingleCourseUI(course);
        } else if (command.equals("a")) {
            runAddCourseUI();
        } else if (command.equals("m")) {
            runIntroUI();
        } else if (command.equals("q")) {
            exitProgram();
        } else {
            invalidFurtherOptionsAddCourseUI(course);
        }
    }

    // MODIFIES: this
    // EFFECTS: notifies user that input was invalid, the next user input will be re-processed
    private void invalidFurtherOptionsAddCourseUI(Course course) {
        System.out.println("Please select a valid input");
        initCommand();
        processFurtherOptionsAddCourse(course);
    }







    // ------ SINGLE COURSE ---------------

    // MODIFIES: this
    // EFFECTS: constructs the singular course user interface
    private void runSingleCourseUI(Course course) {
        displaySingleCourseMenu(course);
        initCommand();
        processSingleCourseCommand(course);
    }

    // EFFECTS: prints singular course information (name, average, tasks), and user options
    private void displaySingleCourseMenu(Course course) {
        System.out.println("\nCourse: " + course.getName());
        System.out.println("Current average: " + course.getAverage());
        if (course.hasTasks()) {
            System.out.println("Enter the name of the task you would like to inspect, or select from other options.");
            System.out.println("\n\tTasks:");
            displayTasks(course);
        } else {
            System.out.println("You currently have no tasks for this course");
        }
        System.out.println("\n\tt -> add Task");
        System.out.println("\td -> delete Course");
        System.out.println("\tb -> back to Courses Interface");
        System.out.println("\tm -> view Main Menu");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: prints all tasks belonging to given course
    private void displayTasks(Course course) {
        List<Task> taskList = course.getTaskList();
        for (Task task : taskList) {
            printTaskForCourse(task);
        }
    }

    // EFFECTS: prints relative information (due date, name, weight, mark) about single task
    private void printTaskForCourse(Task task) {
        String taskName = task.getName();
        int taskMonth = task.getTaskMonth();
        int taskDay = task.getTaskDay();
        double weight = task.getWeight();
        double mark = task.getMark();
        String markRounded = task.getMarkRounded();
        System.out.print("\t" + taskMonth + "/" + taskDay + ": \t " + taskName + "\t Weight: " + weight + "%");
        if (mark < 0) {
            System.out.println("\t Mark: n/a");
        } else {
            System.out.println("\tMark: " + markRounded + "%");
        }
    }

    // EFFECTS: processes user command: redirects user to desired program location based on user
    //          input or notifies user that their input was invalid
    private void processSingleCourseCommand(Course course) {
        if (command.equals("m")) {
            runIntroUI();
        } else if (command.equals("b")) {
            runCoursesUI();
        }  else if (command.equals("t")) {
            runAddTaskUI(course);
        }  else if (command.equals("q")) {
            exitProgram();
        } else if (course.containsTaskWithName(command)) {
            runSingleTaskUI(course.findTaskFromName(command));
        } else if (command.equals("d")) {
            runDeleteCourseUI(course);
        } else {
            invalidSingleCourseInput(course);
        }
    }

    // MODIFIES: this
    // EFFECTS: notifies user that input was invalid, the next user input will be re-processed
    private void invalidSingleCourseInput(Course course) {
        System.out.println("Please select a valid input");
        initCommand();
        processSingleCourseCommand(course);
    }




    // MODIFIES: this
    // EFFECTS: constructs the Add Task user-interface
    private void runAddTaskUI(Course course) {
        displayAddTaskMenu();
        initCommand();
        processAddTaskCommand(course);
    }

    // EFFECTS: prints instructions for adding task
    private void displayAddTaskMenu() {
        System.out.println("\nEnter the task name, task weight, and task due date (mm/dd), separated by a comma");
        System.out.println("For example: 'midterm 1, 25, 12/09'");
        System.out.println("Press b to cancel");
    }

    // MODIFIES: this
    // EFFECTS: processes user input: checks is user input is valid for making new task, if so adds task to course,
    //          otherwise notifies user of invalid input
    private void processAddTaskCommand(Course course) {
        if (command.equals("b")) {
            runSingleCourseUI(course);
        }
        try {
            String[] inputList = command.split(", ");
            String[] monthDayList = inputList[2].split("/");
            String taskName = inputList[0];
            double taskWeight =  Double.parseDouble(inputList[1]);
            int taskMonth = Integer.parseInt(monthDayList[0]);
            int taskDay = Integer.parseInt(monthDayList[1]);
            addTaskToCourse(course, taskName, taskWeight, taskMonth, taskDay);
        } catch (Exception e) {
            System.out.println("Invalid input...");
            invalidTaskInput(course);
        }
    }

    // EFFECTS: checks if user input is valid for making new task, if so adds task to course,
    //          otherwise notifies user of invalid input
    private void addTaskToCourse(Course course, String taskName, double taskWeight, int taskMonth, int taskDay) {
        if (course.containsTaskWithName(taskName)) {
            alreadyContainsTask(course);
        } else if (0 > taskWeight || taskWeight > 100) {
            invalidWeight(course);
        } else if (0 >= taskMonth || taskMonth > 12) {
            invalidMonth(course);
        } else if (0 >= taskDay || taskDay > 31) {
            invalidDay(course);
        } else if (taskName.length() <= 1) {
            invalidLength(course);
        } else if (taskName.substring(0,1).equals(" ")) {
            invalidWhiteSpace(course);
        } else if (taskName.substring(taskName.length() - 1).equals(" ")) {
            invalidWhiteSpace(course);
        } else {
            Task newTask = new Task(taskName, taskWeight, taskMonth, taskDay);
            course.addTask(newTask);
            verifyAddedTask(newTask);
            runSingleCourseUI(course);
        }
    }

    // EFFECTS: prints task-addition verification
    private void verifyAddedTask(Task task) {
        System.out.println("The following course has been added to your semester: " + task.getName());
    }

    // MODIFIES: this
    // EFFECTS: notifies user task weight input was invalid, the next user input will be re-processed
    private void invalidWeight(Course course) {
        System.out.println("Task weight must be between 0 and 100");
        invalidTaskInput(course);
    }

    // MODIFIES: this
    // EFFECTS: notifies user task month input was invalid, the next user input will be re-processed
    private void invalidMonth(Course course) {
        System.out.println("Task month must be between 1 and 12");
        invalidTaskInput(course);
    }

    // MODIFIES: this
    // EFFECTS: notifies user task day input was invalid, the next user input will be re-processed
    private void invalidDay(Course course) {
        System.out.println("Task day must be between 1 and 31");
        invalidTaskInput(course);
    }

    // MODIFIES: this
    // EFFECTS: notifies user task name length input was invalid, the next user input will be re-processed
    private void invalidLength(Course course) {
        System.out.println("Task name must contain more than 1 character");
        invalidTaskInput(course);
    }

    // MODIFIES: this
    // EFFECTS: notifies user task name input was invalid, the next user input will be re-processed
    private void invalidWhiteSpace(Course course) {
        System.out.println("Task name cannot start or end with a space");
        invalidTaskInput(course);
    }

    // MODIFIES: this
    // EFFECTS: notifies user task weight input was invalid, the next user input will be re-processed
    private void alreadyContainsTask(Course course) {
        System.out.println("Course already contains task with that name");
        invalidTaskInput(course);
    }

    // MODIFIES: this
    // EFFECTS: next user input will be re-processed
    private void invalidTaskInput(Course course) {
        initCommand();
        processAddTaskCommand(course);
    }








    // MODIFIES: this
    // EFFECTS: constructs the Delete Course user-interface
    private void runDeleteCourseUI(Course course) {
        displayDeleteCourseMenu(course);
        initCommand();
        processDeleteCourseCommand(course);
    }

    // EFFECTS: prints delete Course options
    private void displayDeleteCourseMenu(Course course) {
        System.out.println("\nAre you sure you want to delete " + course.getName());
        System.out.println("\t y -> yes");
        System.out.println("\t n -> no");
    }

    // MODIFIES: this
    // EFFECTS: processes user command: deletes course if user input demands so, or cancel course deletion,
    //          or notifies user that their input was invalid
    private void processDeleteCourseCommand(Course course) {
        if (command.equals("y")) {
            semester.removeCourse(course);
            System.out.println(course.getName() + " has been deleted");
            runCoursesUI();
        } else if (command.equals("n")) {
            System.out.println("deletion cancelled...");
            runSingleCourseUI(course);
        } else {
            invalidDeleteCourseInput(course);
        }
    }

    // MODIFIES: this
    // EFFECTS: notifies user input was invalid, the next user input will be re-processed
    private void invalidDeleteCourseInput(Course course) {
        System.out.println("Please select a valid input");
        initCommand();
        processDeleteCourseCommand(course);

    }







    // MODIFIES: this
    // EFFECTS: constructs the Single Task user-interface
    private void runSingleTaskUI(Task task) {
        displaySingleTaskMenu(task);
        initCommand();
        processSingleTaskCommand(task);
    }

    // EFFECTS: prints relative information (due date, name, weight, mark) about single task to console,
    //          and presents user options
    private void displaySingleTaskMenu(Task task) {
        System.out.println("\nTask: " + task.getName() + ", " + task.getCourse().getName());
        System.out.println("Due date: " + task.getTaskMonth() + "/" + task.getTaskDay());
        System.out.println("Weight: " + task.getWeight() + "%");
        if (task.getMark() < 0) {
            System.out.println("Mark: n/a");
        } else {
            System.out.println("Mark: " + task.getMarkRounded() + "%");
        }
        System.out.println("\t g -> edit task mark");
        System.out.println("\t d -> delete task");
        System.out.println("\t b -> back to " + task.getCourse().getName() + " interface");
        System.out.println("\t m -> main menu");
        System.out.println("\t q -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command: redirects user to desired program location based on user
    //          input or notifies user that their input was invalid
    private void processSingleTaskCommand(Task task) {
        if (command.equals("m")) {
            runIntroUI();
        } else if (command.equals("b")) {
            runSingleCourseUI(task.getCourse());
        } else if (command.equals("g")) {
            runEnterGradeUI(task);
        }  else if (command.equals("d")) {
            runDeleteTaskUI(task);
        } else if (command.equals("q")) {
            exitProgram();
        } else {
            invalidSingleTaskInput(task);
        }
    }

    // MODIFIES: this
    // EFFECTS: notifies user input was invalid, the next user input will be re-processed
    private void invalidSingleTaskInput(Task task) {
        System.out.println("Please select a valid input");
        initCommand();
        processSingleTaskCommand(task);
    }








    // MODIFIES: this
    // EFFECTS: constructs the Enter Task Grade user-interface
    private void runEnterGradeUI(Task task) {
        displayEnterGradeMenu(task);
        initCommand();
        processEnterGradeCommand(task);
    }

    // EFFECTS: prints instructions for adding/editing given task grade
    private void displayEnterGradeMenu(Task task) {
        System.out.println("\nPlease enter the percent grade you received in " + task.getName());
        System.out.println("For example, '82.53'");
        System.out.println("Press b to cancel");
    }

    // MODIFIES: this
    // EFFECTS: processes user command: if grade is valid, adds grade to task, or notifies user that input was invalid
    private void processEnterGradeCommand(Task task) {
        if (command.equals("b")) {
            runSingleTaskUI(task);
        }
        try {
            double mark = Double.parseDouble(command);
            if (mark > 100 || mark < 0) {
                System.out.println("Grade must be between 0 and 100");
                invalidGradeInput(task);

            } else {
                task.setMark(mark);
                runSingleTaskUI(task);
            }
        } catch (Exception e) {
            System.out.println("Please enter a number");
            invalidGradeInput(task);
        }
    }

    // MODIFIES: this
    // EFFECTS: notifies user input was invalid, the next user input will be re-processed
    private void invalidGradeInput(Task task) {
        initCommand();
        processEnterGradeCommand(task);
    }






    

    // MODIFIES: this
    // EFFECTS: constructs the Delete Task user-interface
    private void runDeleteTaskUI(Task task) {
        displayDeleteTaskMenu(task);
        initCommand();
        processDeleteTaskCommand(task);
    }

    // EFFECTS: prints delete task instructions
    private void displayDeleteTaskMenu(Task task) {
        System.out.println("\nAre you sure you want to delete " + task.getName());
        System.out.println("\t y -> yes");
        System.out.println("\t n -> no");
    }

    // MODIFIES: this
    // EFFECTS: processes user command: deletes task if user input demands so, or cancels task deletion, or
    //          notifies user that input was invalid
    private void processDeleteTaskCommand(Task task) {
        if (command.equals("y")) {
            Course course = task.getCourse();
            course.removeTask(task);
            System.out.println(task.getName() + " has been deleted");
            runSingleCourseUI(course);
        } else if (command.equals("n")) {
            System.out.println("deletion cancelled...");
            runSingleTaskUI(task);
        } else {
            invalidDeleteTaskInput(task);
        }
    }

    // MODIFIES: this
    // EFFECTS: notifies user input was invalid, the next user input will be re-processed
    private void invalidDeleteTaskInput(Task task) {
        System.out.println("Please select a valid input");
        initCommand();
        processDeleteTaskCommand(task);

    }


}

