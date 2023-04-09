package ui;

import model.Course;
import model.Event;
import model.EventLog;
import model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


import java.awt.Image;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;



// Represents SemesterTracker Graphical User Interface
public class SemesterTrackerGUI extends SemesterTracker {

    private CardLayout cardLayout;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // EFFECTS: Pre-constructs all GUIs for all possible panels of program, shows the Intro GUI
    public SemesterTrackerGUI() {
        super("dummy");
        setTitle("SemesterTracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        cardLayout = new CardLayout();
        getContentPane().setLayout(cardLayout);
        getContentPane().add(makeIntroGUI(), "intro");
        getContentPane().add(makeAllTasksGUI(), "allTasks");
        getContentPane().add(makeAllCoursesGUI(), "allCourses");
        getContentPane().add(makeGoodbyeGUI(), "goodbye");

        makeCoursePages();
        setLocationRelativeTo(null);
        setVisible(true);
        cardLayout.show(getContentPane(), "intro");
    }

    // MODIFIES: this
    // EFFECTS: Constructs panel for each course-page and adds it to ContentPanes
    private void makeCoursePages() {
        for (Course course : semester.getCourseList()) {
            getContentPane().add(makeSingleCourseUI(course), course.getName());
        }
    }

    // EFFECTS: Constructs panel which displays before user exits program
    // images source: https://pixabay.com/photos/frogs-farewell-bicycle-trolley-1701047/
    // image is free for personal/commerical use with no attribution required
    private JPanel makeGoodbyeGUI() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        Image img = null;
        try {
            img = ImageIO.read(new File("data/goodbye.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(getWidth(), getHeight(),
                Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        label.setIcon(imageIcon);
        panel.add(label);
        return panel;

    }

    // EFFECTS: Constructs and returns a Quit button which exits program when clicked
    private JButton getQuitButton() {
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            // MODIFIES: This
            // EFFECTS: displays Goodbye image for 3 seconds, then exits program
            public void actionPerformed(ActionEvent e) {
                sayGoodbyeUI();
            }
        });
        return quitButton;
    }

    // MODIFIES: this
    // EFFECTS: Displays Goodbye image for 3 seconds, then exits program
    private void sayGoodbyeUI() {
        cardLayout.show(getContentPane(), "goodbye");
        ActionListener listener = new ActionListener() {
            @Override
            // MODIFIES: this
            // EFFECTS: exits program
            public void actionPerformed(ActionEvent e) {
                EventLog el = EventLog.getInstance();
                System.out.println("Event Log: ");
                for (Event next : el) {
                    System.out.println(next.toString());
                }
                System.exit(0);
            }
        };
        Timer timer = new Timer(3000, listener);
        timer.setRepeats(false);
        timer.start();
    }




    // EFFECTS: Constructs and returns a Main Menu button which directs user to Main Menu when clicked
    private JButton getMainMenuButton() {
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(new ActionListener() {
            // MODIFIES: this
            // EFFECTS: displays Main Menu GUI
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(getContentPane(), "intro");
            }
        });
        return mainMenuButton;
    }

    // EFFECTS: Returns a light blue colour used for background of GUI
    private Color lightBlue() {
        return new Color(224, 255, 255);
    }








    // ----- INTRO ---------

    // EFFECTS: Constructs and returns Main Menu panel
    private JPanel makeIntroGUI() {
        JPanel panel = new JPanel();
        panel.setBackground(lightBlue());
        panel.setLayout(new GridLayout(6, 1));

        JLabel welcomeStatement = new JLabel("SemesterTracker");
        welcomeStatement.setFont(new Font("Arial", Font.ITALIC, 40));
        welcomeStatement.setHorizontalAlignment(SwingConstants.CENTER);


        panel.add(welcomeStatement);
        welcomeStatement.requestFocusInWindow();

        JButton allCoursesButton = new JButton("View All Courses");
        panel.add(allCoursesButton);
        JButton allTasksButton = new JButton("View All Tasks");
        panel.add(allTasksButton);

        JButton saveSemesterButton = new JButton("Save Your Semester");
        saveSemesterEvent(saveSemesterButton);
        panel.add(saveSemesterButton);

        JButton loadSemesterButton = new JButton("Load Your Semester");
        loadSemesterEvent(loadSemesterButton);
        panel.add(loadSemesterButton);
        JButton quitButton = getQuitButton();

        addEventListenersIntroUI(allCoursesButton, allTasksButton);
        panel.add(quitButton);

        return panel;
    }

    // EFFECTS: Sets loadSemesterButton's click event to load saved JSON data into program
    private void loadSemesterEvent(JButton loadSemesterButton) {
        loadSemesterButton.addActionListener(new ActionListener() {
            // MODIFIES: this
            // EFFECTS: loads semester from file
            public void actionPerformed(ActionEvent e) {
                if (loadSemester()) {
                    JOptionPane.showMessageDialog(null, "Semester Loaded");
                    getContentPane().add(makeIntroGUI(), "intro");
                    getContentPane().add(makeAllTasksGUI(), "allTasks");
                    getContentPane().add(makeAllCoursesGUI(), "allCourses");
                    makeCoursePages();
                } else {
                    JOptionPane.showMessageDialog(null, "Could not Load Semester");
                }
            }
        });
    }

    // EFFECTS: Sets saveSemesterButton's click event to save program data as JSON data
    private void saveSemesterEvent(JButton saveSemesterButton) {
        saveSemesterButton.addActionListener(new ActionListener() {
            // EFFECTS: saves semester to file
            public void actionPerformed(ActionEvent e) {
                System.out.println("hi");
                if (saveSemester()) {
                    JOptionPane.showMessageDialog(null, "Semester Saved");
                } else {
                    JOptionPane.showMessageDialog(null, "Could not Save Semester");
                }
            }
        });
    }


    // EFFECTS: Sets allCoursesButton's click event to trigger All Courses GUI
    //          Sets alLTasksButton's click event to trigger All Tasks GUI
    private void addEventListenersIntroUI(JButton allCoursesButton, JButton allTasksButton) {
        allCoursesButton.addActionListener(new ActionListener() {
            // MODIFIES: this
            // EFFECTS: displays All Courses GUI
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(getContentPane(), "allCourses");
            }
        });
        allTasksButton.addActionListener(new ActionListener() {
            // MODIFIES: this
            // EFFECTS: displays All Tasks GUI
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(getContentPane(), "allTasks");
            }
        });
    }







    //-------- ALL TASKS --------------


    // EFFECTS: Constructs All Tasks panel and returns it
    private JPanel makeAllTasksGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        JPanel listPanel = new JPanel(new GridLayout(0, 1));
        makeAllTasksListGUI(listPanel);
        JScrollPane scrollListPane = new JScrollPane(listPanel);
        GridBagConstraints c1 = formatAllTasksListPanel();
        mainPanel.add(scrollListPane, c1);

        JPanel buttonPanel = new JPanel();
        JButton mainMenuButton = getMainMenuButton();
        JButton quitButton = getQuitButton();
        buttonPanel.add(mainMenuButton);
        buttonPanel.add(quitButton);

        GridBagConstraints c2 = formatAllTasksButtonPanel();
        mainPanel.add(buttonPanel, c2);

        return mainPanel;
    }

    // EFFECTS: Creates and returns GridBadConstains for All Tasks GUI's Options Menu
    private GridBagConstraints formatAllTasksButtonPanel() {
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 1;
        c2.weightx = 1.0;
        c2.weighty = 0.05;
        c2.fill = GridBagConstraints.BOTH;
        return c2;
    }

    // EFFECTS: Creates and returns GridBadConstains for All Tasks GUI's Task List
    private  GridBagConstraints formatAllTasksListPanel() {
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 0;
        c1.weightx = 1.0;
        c1.weighty = 0.95;
        c1.fill = GridBagConstraints.BOTH;
        return c1;
    }

    // EFFECTS: Constructs the list of tasks panel for All Tasks GUI
    private void makeAllTasksListGUI(JPanel listPanel) {
        ArrayList<Task> allTasks = semester.sortAllTasksByDate(semester.generateAllTasks());
        JPanel header = new JPanel();
        makeAllTasksHeaderGUI(listPanel, header);
        for (Task task : allTasks) {
            JPanel row = new JPanel();
            row.setLayout(new GridLayout(1, 5));
            JLabel due = new JLabel(task.getTaskMonth() + "/" + task.getTaskDay());
            row.add(due);
            JLabel name = new JLabel(task.getName());
            row.add(name);
            JLabel weight = new JLabel("Weight: " + task.getWeight());
            row.add(weight);
            JLabel course = new JLabel(task.getCourse().getName());
            row.add(course);
            listPanel.add(row);
        }
    }

    // EFFECTS: Constructs the table-header for the list of tasks for the All Tasks GUI
    private  void makeAllTasksHeaderGUI(JPanel listPanel, JPanel header) {
        header.setLayout(new GridLayout(1, 5));
        JLabel dueHeader = new JLabel("DUE DATE");
        header.add(dueHeader);
        JLabel nameHeader = new JLabel("TASK NAME");
        header.add(nameHeader);
        JLabel weightHeader = new JLabel("WEIGHT");
        header.add(weightHeader);
        JLabel courseHeader = new JLabel("COURSE NAME");
        header.add(courseHeader);
        listPanel.add(header);
    }








    // -------- ALL COURSES -------------

    // EFFECTS: Constructs and returns All Courses GUI Panel
    private JPanel makeAllCoursesGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(lightBlue());
        mainPanel.setLayout(new GridBagLayout());
        JLabel headerText = new JLabel("Course List");
        headerText.setFont(new Font("Arial", Font.ITALIC, 40));
        headerText.setHorizontalAlignment(JLabel.CENTER);
        headerText.setVerticalAlignment(JLabel.CENTER);
        GridBagConstraints headerConst = formatAllCoursesHeader();
        mainPanel.add(headerText, headerConst);
        JPanel coursePanel = makeCoursesPanel();
        GridBagConstraints courseConst = formatAllCoursesListPanel();
        mainPanel.add(coursePanel, courseConst);
        JPanel buttonPanel = new JPanel();
        JButton mainMenuButton = getMainMenuButton();
        JButton quitButton = getQuitButton();
        JButton addCourseButton = getAddCourseButton();
        buttonPanel.add(addCourseButton);
        buttonPanel.add(mainMenuButton);
        buttonPanel.add(quitButton);
        GridBagConstraints extraButtonConst = formatAllCoursesButtonPanel();
        mainPanel.add(buttonPanel, extraButtonConst);
        return mainPanel;
    }

    // EFFECTS: Constructs and returns a Add Course Button which allows user to add new course when clicked
    private JButton getAddCourseButton() {
        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.addActionListener(new ActionListener() {
            // MODIFIES: this
            // EFFECTS: prompts user to add new course to semester
            //          creates new course with same name as user input
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Enter the course name: "));
                JTextField nameField = new JTextField();
                panel.add(nameField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Add Course",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String name = nameField.getText();
                    JPanel errorPanel = new JPanel();
                    addCourseOrDisplayError(name, errorPanel);
                }
            }
        });
        return addCourseButton;
    }

    // MODIFIES: this
    // EFFECTS: Checks if name is a valid course name, if yes creates new course with name and adds to semester
    //          if no, displays error message to user
    private void addCourseOrDisplayError(String name, JPanel errorPanel) {
        if (name.length() <= 1) {
            courseNameLengthError(errorPanel);
        } else if (name.substring(0, 1).equals(" ")) {
            courseWhiteSpaceStartError(errorPanel);
        } else if (name.substring(name.length() - 1).equals(" ")) {
            courseWhiteSpaceEndError(errorPanel);
        } else if (semester.containsCourseWithName(name.toLowerCase())) {
            courseNameExistsError(errorPanel);
        } else {
            makeNewCourse(name);
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates new course with name and adds to semester, creates new GUI for course and adds to Content Pane
    //          Re-displays all Courses GUI
    private void makeNewCourse(String name) {
        Course newCourse = new Course(name.toLowerCase());
        semester.addCourse(newCourse);
        getContentPane().add(makeSingleCourseUI(newCourse), newCourse.getName());
        getContentPane().add(makeAllCoursesGUI(), "allCourses");
        cardLayout.show(getContentPane(), "allCourses");
    }

    // EFFECTS: Notifies user with Error Message that inputted course name is too short
    private void courseNameLengthError(JPanel errorPanel) {
        JLabel error = new JLabel("Course name length must be > 1");
        errorPanel.add(error);
        JOptionPane.showConfirmDialog(null, errorPanel, "Error",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        cardLayout.show(getContentPane(), "allCourses");
    }

    // EFFECTS: Notifies user with Error Message that inputted course name already exists
    private void courseNameExistsError(JPanel errorPanel) {
        JLabel error = new JLabel("Course name already exists");
        errorPanel.add(error);
        JOptionPane.showConfirmDialog(null, errorPanel, "Error",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        cardLayout.show(getContentPane(), "allCourses");
    }

    // EFFECTS: Notifies user with Error Message that inputted course name cannot end with whitespace
    private void courseWhiteSpaceEndError(JPanel errorPanel) {
        JLabel error = new JLabel("Course name cannot end with a space");
        errorPanel.add(error);
        JOptionPane.showConfirmDialog(null, errorPanel, "Error",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        cardLayout.show(getContentPane(), "allCourses");
    }

    // EFFECTS: Notifies user with Error Message that inputted course name cannot start with whitespace
    private void courseWhiteSpaceStartError(JPanel errorPanel) {
        JLabel error = new JLabel("Course name cannot start with a space");
        errorPanel.add(error);
        JOptionPane.showConfirmDialog(null, errorPanel, "Error",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        cardLayout.show(getContentPane(), "allCourses");
    }



    // EFFECTS: Creates and returns GridBagConstraints for the All Courses GUI's header
    private GridBagConstraints formatAllCoursesHeader() {
        GridBagConstraints courseConst = new GridBagConstraints();
        courseConst.gridx = 0;
        courseConst.gridy = 0;
        courseConst.weightx = 0.5;
        courseConst.weighty = 0.20;
        courseConst.fill = GridBagConstraints.BOTH;
        return courseConst;
    }



    // EFFECTS: Creates and returns GridBagConstraints for the All Courses GUI's option menu
    private GridBagConstraints formatAllCoursesButtonPanel() {
        GridBagConstraints courseConst = new GridBagConstraints();
        courseConst.gridx = 0;
        courseConst.gridy = 2;
        courseConst.weightx = 0.5;
        courseConst.weighty = 0.05;
        courseConst.fill = GridBagConstraints.BOTH;
        return courseConst;
    }

    // EFFECTS: Creates and returns GridBagConstraints for the All Courses GUI's course list
    private GridBagConstraints formatAllCoursesListPanel() {
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 1;
        c1.weightx = 0.5;
        c1.weighty = 0.65;
        c1.fill = GridBagConstraints.BOTH;
        return c1;
    }

    // EFFECTS: Constructs and returns the course-list for the All Courses GUI
    private JPanel makeCoursesPanel() {
        JPanel coursePanel = new JPanel(new GridLayout(1,0));
        coursePanel.setBackground(lightBlue());
        List<Course> allCourses = semester.getCourseList();
        for (Course course : allCourses) {
            JPanel column = new JPanel(new GridBagLayout());
            column.setBackground(lightBlue());

            JButton courseButton = new JButton(course.getName().toUpperCase());
            GridBagConstraints buttonConst = formatCourseButton();
            makeCourseEnterEvent(courseButton, course.getName());
            column.add(courseButton, buttonConst);

            JButton removeCourseButton = new JButton("Remove");
            GridBagConstraints removeButtonConst = formatRemoveCourseButton();
            makeRemoveCourseEvent(course, removeCourseButton);

            column.add(removeCourseButton, removeButtonConst);
            coursePanel.add(column);
        }

        return coursePanel;
    }

    // EFFECTS: Sets courseButton's click event to trigger Single Course GUI for given course (name)
    private void makeCourseEnterEvent(JButton courseButton, String courseName) {
        courseButton.addActionListener(new ActionListener() {
            // EFFECTS: displays Single Course GUI for given course
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(getContentPane(), courseName);
            }
        });
    }

    // EFFECTS: Sets removeCourseButton's click event to remove given course from semester
    private void makeRemoveCourseEvent(Course course, JButton removeCourseButton) {
        removeCourseButton.addActionListener(new ActionListener() {
            // MODIFIES: this
            // EFFECTS: removes given course from semester course list, displays All Courses GUI
            public void actionPerformed(ActionEvent e) {
                semester.removeCourse(course);
                getContentPane().add(makeAllCoursesGUI(), "allCourses");
                getContentPane().add(makeAllTasksGUI(), "allTasks");
                cardLayout.show(getContentPane(), "allCourses");
            }
        });
    }

    // EFFECTS: Creates and returns GridBagConstraints for remove-course button
    private GridBagConstraints formatRemoveCourseButton() {
        GridBagConstraints removeButtonConst = new GridBagConstraints();
        removeButtonConst.gridx = 0;
        removeButtonConst.gridy = 2;
        removeButtonConst.weightx = 1;
        removeButtonConst.weighty = 1;
        removeButtonConst.fill = GridBagConstraints.BOTH;
        return removeButtonConst;
    }

    // EFFECTS: Creates and returns GridBagConstraints for enter-course button
    private  GridBagConstraints formatCourseButton() {
        GridBagConstraints buttonConst = new GridBagConstraints();
        buttonConst.gridx = 0;
        buttonConst.gridy = 0;
        buttonConst.weightx = 1;
        buttonConst.weighty = 19;
        buttonConst.fill = GridBagConstraints.BOTH;
        return buttonConst;
    }











    // --- SINGLE COURSE -------

    // EFFECTS: Constructs and returns for Single Course GUI panel for given course
    private JPanel makeSingleCourseUI(Course course) {
        JPanel mainPanel = new JPanel(new GridBagLayout());

        JLabel courseName = new JLabel(course.getName());
        courseName.setFont(new Font("Arial", Font.BOLD, 40));
        GridBagConstraints nc = formatSingleCourseHeader();
        mainPanel.add(courseName, nc);

        JLabel average = new JLabel("Current Average: " + course.getAverage());
        GridBagConstraints ac = formatCourseAverage();
        mainPanel.add(average, ac);

        JPanel listPanel = new JPanel(new GridLayout(0, 1));
        makeCourseTaskListGUI(course, listPanel);
        JScrollPane scrollListPane = new JScrollPane(listPanel);
        GridBagConstraints c1 = formatCourseTaskListPanel();
        mainPanel.add(scrollListPane, c1);

        JPanel buttonPanel = makeSingleCourseButtonPanel(course);
        GridBagConstraints c2 = formatCourseTasksButtonPanel();
        mainPanel.add(buttonPanel, c2);

        return mainPanel;

    }

    // EFFECTS: Constructs and returns the options menu for the Single Course panel for given course
    private JPanel makeSingleCourseButtonPanel(Course course) {
        JPanel buttonPanel = new JPanel();
        JButton mainMenuButton = getMainMenuButton();
        JButton quitButton = getQuitButton();
        JButton addTaskButton = addTaskButton(course);
        JButton backToAllCourses = backToCoursesButton();
        buttonPanel.add(addTaskButton);
        buttonPanel.add(backToAllCourses);
        buttonPanel.add(mainMenuButton);
        buttonPanel.add(quitButton);
        return buttonPanel;
    }

    // EFFECTS: Creates and returns the GridBragConstraints for the course average on the Single Course panel
    private GridBagConstraints formatCourseAverage() {
        GridBagConstraints ac = new GridBagConstraints();
        ac.gridx = 0;
        ac.gridy = 1;
        ac.fill = GridBagConstraints.BOTH;
        return ac;
    }

    // EFFECTS: Creates and returns the GridBadConstrains for the course name header on the Single Course panel
    private GridBagConstraints formatSingleCourseHeader() {
        GridBagConstraints nc = new GridBagConstraints();
        nc.gridx = 0;
        nc.gridy = 0;
        nc.fill = GridBagConstraints.BOTH;
        return nc;
    }

    // EFFECTS: Constructs and returns a button for Single Course panel which triggers the All-Courses GUI when clicked
    private JButton backToCoursesButton() {
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            // EFFECTS: Diplays All Courses GUI
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(getContentPane(), "allCourses");
            }
        });

        return back;
    }

    // EFFECTS: Constructs and returns a button for Single Course panel which prompts user to add new task
    private JButton addTaskButton(Course course) {
        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.addActionListener(new ActionListener() {
            // MODIFIES: this
            // EFFECTS: prompts user to add new task
            //          if task details are valid inputs, then creates new task for given course,
            //          then displays Single Course GUI for given course
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Enter the task name: "));
                JTextField nameField = new JTextField();
                panel.add(nameField);
                panel.add(new JLabel("Enter the task weight: "));
                JTextField nameField2 = new JTextField();
                panel.add(nameField2);
                panel.add(new JLabel("Enter the month the task is due (mm): "));
                JTextField nameField3 = new JTextField();
                panel.add(nameField3);
                panel.add(new JLabel("Enter the day the task is due (dd): "));
                JTextField nameField4 = new JTextField();
                panel.add(nameField4);
                int result = JOptionPane.showConfirmDialog(null, panel, "Add Task",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                addTaskToTaskList(nameField, nameField2, nameField3, nameField4, result, course);
            }
        });
        return addTaskButton;
    }

    // EFFECTS: Adds task with given properties (name, weight, month, day) to course if all properties are valid
    //          or else notifies user that an inputed property is invalid
    //          Updates the Single Course GUI for given course, and the All Tasks GUI to include new task
    private void addTaskToTaskList(JTextField name, JTextField weight,
                                   JTextField month, JTextField day, int result, Course course) {
        if (result == JOptionPane.OK_OPTION) {
            try {
                String taskName = name.getText();
                Double taskWeight = Double.parseDouble(weight.getText());
                int taskMonth = Integer.parseInt(month.getText());
                int taskDay = Integer.parseInt(day.getText());
                addTaskToCourse(course, taskName, taskWeight, taskMonth, taskDay);
                getContentPane().add(makeAllTasksGUI(), "allTasks");
                getContentPane().add(makeSingleCourseUI(course), course.getName());
                cardLayout.show(getContentPane(), course.getName());
            } catch (Exception ex) {
                JPanel errorPanel = new JPanel();
                JLabel error = new JLabel("Ensure that weight/month/day are integers");
                errorPanel.add(error);
                JOptionPane.showConfirmDialog(null, errorPanel, "name",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                cardLayout.show(getContentPane(), course.getName());

            }

        }
    }


    // EFFECTS: checks if user input is valid for making new task, if so adds task to course,
    //          otherwise notifies user of invalid input
    private void addTaskToCourse(Course course, String taskName, double taskWeight, int taskMonth, int taskDay) {
        JPanel errorPanel = new JPanel();
        if (course.containsTaskWithName(taskName)) {
            taskNameExistsError(course, errorPanel);
        } else if (0 > taskWeight || taskWeight > 100) {
            taskWeightRangeError(course, errorPanel);
        } else if (0 >= taskMonth || taskMonth > 12) {
            taskMonthRangeError(course, errorPanel);
        } else if (0 >= taskDay || taskDay > 31) {
            taskDayRangeError(course, errorPanel);
        } else if (taskName.length() <= 1) {
            taskLengthError(course, errorPanel);
        } else if (taskName.substring(0,1).equals(" ")) {
            taskWhiteSpaceErrorFront(course, errorPanel);
        } else if (taskName.substring(taskName.length() - 1).equals(" ")) {
            taskWhiteSpaceErrorBack(course, errorPanel);
        } else {
            Task newTask = new Task(taskName, taskWeight, taskMonth, taskDay);
            course.addTask(newTask);
        }
    }

    // EFFECTS: Notifies user with Error Message that inputted Task name already exists
    private void taskNameExistsError(Course course, JPanel errorPanel) {
        JLabel error = new JLabel("Task name already exists");
        errorPanel.add(error);
        JOptionPane.showConfirmDialog(null, errorPanel, "Error",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        cardLayout.show(getContentPane(), course.getName());
    }

    // EFFECTS: Notifies user with Error Message that inputted Task Weight must be between 1 and 100
    private void taskWeightRangeError(Course course, JPanel errorPanel) {
        JLabel error = new JLabel("Weight must be between 1 and 100");
        errorPanel.add(error);
        JOptionPane.showConfirmDialog(null, errorPanel, "Error",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        cardLayout.show(getContentPane(), course.getName());
    }

    // EFFECTS: Notifies user with Error Message that inputted Task Month must be between 1 and 12
    private void taskMonthRangeError(Course course, JPanel errorPanel) {
        JLabel error = new JLabel("Month must be between 1 and 12");
        errorPanel.add(error);
        JOptionPane.showConfirmDialog(null, errorPanel, "Error",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        cardLayout.show(getContentPane(), course.getName());
    }

    // EFFECTS: Notifies user with Error Message that inputted Task Day must be between 1 and 31
    private void taskDayRangeError(Course course, JPanel errorPanel) {
        JLabel error = new JLabel("Day must be between 1 and 31");
        errorPanel.add(error);
        JOptionPane.showConfirmDialog(null, errorPanel, "Error",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        cardLayout.show(getContentPane(), course.getName());
    }

    // EFFECTS: Notifies user with Error Message that inputted Task Name length must be greater than 1
    private void taskLengthError(Course course, JPanel errorPanel) {
        JLabel error = new JLabel("Name length must be greater than 1");
        errorPanel.add(error);
        JOptionPane.showConfirmDialog(null, errorPanel, "Error",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        cardLayout.show(getContentPane(), course.getName());
    }

    // EFFECTS: Notifies user with Error Message that inputted Task Name cannot begin with a white space
    private void taskWhiteSpaceErrorFront(Course course, JPanel errorPanel) {
        JLabel error = new JLabel("Name cannot begin with a white space");
        errorPanel.add(error);
        JOptionPane.showConfirmDialog(null, errorPanel, "Error",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        cardLayout.show(getContentPane(), course.getName());
    }

    // EFFECTS: Notifies user with Error Message that inputted Task Name cannot end with a whitespace
    private void taskWhiteSpaceErrorBack(Course course, JPanel errorPanel) {
        JLabel error = new JLabel("Name cannot end with a whitespace");
        errorPanel.add(error);
        JOptionPane.showConfirmDialog(null, errorPanel, "Error",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        cardLayout.show(getContentPane(), course.getName());
    }

    // EFFECTS: Creates and returns the GridBadConstrains for the task list on Single Course panel
    private GridBagConstraints formatCourseTaskListPanel() {
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 2;
        c1.weightx = 1.0;
        c1.weighty = 16;
        c1.gridheight = 1;
        c1.fill = GridBagConstraints.BOTH;
        return c1;

    }

    // EFFECTS: Creates and returns the GridBadConstrains for the button panel on Single Course panel
    private GridBagConstraints formatCourseTasksButtonPanel() {
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 3;
        c2.weightx = 1.0;
        c2.weighty = 1;
        c2.fill = GridBagConstraints.BOTH;
        return c2;
    }

    // EFFECTS: Creates the task list panel for Single Course GUI for given course
    private void makeCourseTaskListGUI(Course course, JPanel listPanel) {
        List<Task> allTasks = course.getTaskList();
        JPanel header = new JPanel();
        makeCourseTaskHeaderGUI(listPanel, header);
        for (Task task : allTasks) {
            JPanel row = new JPanel(new GridLayout(0, 6));
            JLabel due = new JLabel(task.getTaskMonth() + "/" + task.getTaskDay());
            row.add(due);
            JLabel name = new JLabel(task.getName());
            row.add(name);
            JLabel weight = new JLabel("" + task.getWeight());
            row.add(weight);
            displayMark(task, row);
            JPanel optionPanel = new JPanel(new GridBagLayout());
            makeGradeTaskButton(course, task, optionPanel);
            JPanel optionPanelTwo = new JPanel(new GridBagLayout());
            makeRemoveTaskButton(course, task, optionPanelTwo);
            row.add(optionPanel);
            row.add(optionPanelTwo);
            row.setPreferredSize(new Dimension(listPanel.getWidth(), 30));
            row.setMinimumSize(new Dimension(listPanel.getWidth(), 30));
            listPanel.add(row);
        }
    }

    // EFFECTS: Displays given task's grade on task list panel, or n/a if task is ungraded
    private static void displayMark(Task task, JPanel row) {
        JLabel mark;
        if (task.getMark() < 0) {
            mark = new JLabel("n/a");
        } else {
            mark = new JLabel("" + task.getMark());
        }
        row.add(mark);
    }

    // EFFECTS: Constructs a remove task button, which removes given task when clicked
    private void makeRemoveTaskButton(Course course, Task task, JPanel optionPanelTwo) {
        JButton remove = new JButton("Remove");
        remove.addActionListener(new ActionListener() {
            // MODIFIES: this
            // EFFECTS: removes given task from given course, re-displays Single Course GUI for given course
            public void actionPerformed(ActionEvent e) {
                course.removeTask(task);
                getContentPane().add(makeSingleCourseUI(course), course.getName());
                getContentPane().add(makeAllTasksGUI(), "allTasks");
                cardLayout.show(getContentPane(), course.getName());
            }
        });

        GridBagConstraints gbcRemove = new GridBagConstraints();
        gbcRemove.gridx = 0;
        gbcRemove.gridy = 0;
        gbcRemove.fill = GridBagConstraints.BOTH;
        optionPanelTwo.add(remove, gbcRemove);
    }

    // EFFECTS: Constructs Add Grade Button for given task on the Single Course GUI
    private void makeGradeTaskButton(Course course, Task task, JPanel optionPanel) {
        JButton grade = new JButton("Edit Grade");
        GridBagConstraints gbcAdd = new GridBagConstraints();
        gbcAdd.gridx = 0;
        gbcAdd.gridy = 0;
        gbcAdd.fill = GridBagConstraints.BOTH;
        optionPanel.add(grade, gbcAdd);
        makeAddGradeButtonEvent(course, task, grade);
    }

    // EFFECTS: Set's the Add Grade Button event to prompt user to input task grade when clicked
    private void makeAddGradeButtonEvent(Course course, Task task, JButton grade) {
        grade.addActionListener(new ActionListener() {
            // MODIFIES: this
            // EFFECTS: changes given task grade to user input, or displays error message if task grade invalid
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Enter the grade received: "));
                JTextField nameField = new JTextField();
                panel.add(nameField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Change Grade",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        setGrade(nameField, task, course);
                    } catch (Exception ex) {
                        cannotSetGradeMessage();

                    }
                }
            }
        });
    }

    // EFFECTS: Notifies user via error message that their task-grade input was invalid
    private  void cannotSetGradeMessage() {
        JPanel errorPanel = new JPanel();
        JLabel cannotGrade = new JLabel("Grade must be a number");
        errorPanel.add(cannotGrade);
        JOptionPane.showConfirmDialog(null, cannotGrade, "Error",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: Adds grade to given task
    private void setGrade(JTextField nameField, Task task, Course course) {
        Double grade = Double.parseDouble(nameField.getText());
        task.setMark(grade);
        getContentPane().add(makeSingleCourseUI(course), course.getName());
        cardLayout.show(getContentPane(), course.getName());
    }

    // EFFECTS: Constructs the table header for the task list on the Single Courses GUI
    private void makeCourseTaskHeaderGUI(JPanel listPanel, JPanel header) {
        header.setLayout(new GridLayout(0, 6));
        JLabel dueHeader = new JLabel("DUE DATE");
        header.add(dueHeader);
        JLabel nameHeader = new JLabel("TASK NAME");
        header.add(nameHeader);
        JLabel weightHeader = new JLabel("WEIGHT");
        header.add(weightHeader);
        JLabel gradeHeader = new JLabel("GRADE");
        header.add(gradeHeader);
        JLabel blankOne = new JLabel("");
        header.add(blankOne);
        JLabel blankTwo = new JLabel("");
        header.add(blankTwo);
        listPanel.add(header);
    }



}
