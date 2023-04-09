# SemesterTracker
### Take learning into your own hands!
**SemesterTracker is the perfect app for students** who want to stay on top of 
their coursework and take control of their academic progress. With this app,
users can easily create new courses and add tasks to track their assignments,
tests, and deadlines. Whether you need to calculate your current average or
keep tabs on all of your upcoming tasks, SemesterTracker has got you covered. 
And with the ability to edit, remove, and inspect courses and tasks, you'll 
never have to worry about missing a deadline or forgetting an important assignment.
Take control of your academic journey with SemesterTracker today!


_Why this project?_  
As a student who is tired of the limitations of traditional course management tools
like Canvas, this project appeals to me as it offers a personalized and flexible way to keep track of 
my academic progress. With SemesterTracker, I can customize my experience to fit 
my needs.




## User Stories:
- As a user, I would like to create a new course and add the course to my SemesterTracker
- As a user, I would like to add a new task to a course, specifying its name, weight, and due-date
- As a user, I would like to edit a task, specifying the grade I received in the task
- As a user, I would like to view my current average for a course (for all marked tasks so far)
- As a user, I would like to view all tasks for the course in chronological order based on due-date, despite what order I added the tasks to my tracker
- As a user, I would like to see all my upcoming tasks, for all combined courses, in chronological order, despite what order I added the tasks/courses to my tracker
- As a user, I would like to remove courses and/or tasks that I am tracking
  As a user, I want to be able to save my semester to file (if I so choose)
  As a user, I want to be able to be able to load my semester from file (if I so choose)

## Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by navigating to View All Courses UI, and clicking add Course
- You can generate the second required action related to adding Xs to a Y by navigating to View All Courses UI, and clicking remove under a Course
- You can locate my visual component by quitting the program via the quit button
- You can save the state of my application by clicking Save in the Main Menu
- You can reload the state of my application by clicking Load in the Main Menu

## Phase 4: Task 2
Event Log:  <br />
Fri Apr 07 11:49:10 PDT 2023 <br />
Added course: cpsc210 <br />
Fri Apr 07 11:49:17 PDT 2023 <br />
Added course: cpsc313 <br />
Fri Apr 07 11:49:22 PDT 2023 <br />
Added course: math303 <br />
Fri Apr 07 11:49:24 PDT 2023 <br />
Removed course: cpsc313 <br />
Fri Apr 07 11:49:25 PDT 2023 <br />
Removed course: cpsc210 <br />

## Phase 4: Task 3
If I were to refactor a portion of my code, I would focus on redesigning the user interface, specifically the SemesterTracker class and the 
SemesterTrackerGUI class. While discussing the changes I would make, I will be using the SemesterTracker class as an example, but the same
changes would apply to the SemesterTrackerGUI class as well.<br />

Currently, the SemesterTracker class contains all the code related to my console interface. With almost 900 lines of code, navigating this 
class becomes quite difficult for a developer. To make the code more manageable, 
I would refactor it into multiple classes that extend a supertype abstract ConsoleUI class. Each class would represent a different interface 
or section of the program. For example, there would be a MainMenuUI class, an AllCoursesUI class,
an AllTasksUI class, a SingleCourseUI class, and a SingleTaskUI class. These classes would represent the different sections of the program
that a user can explore. The superclass ConsoleUI would contain fields and methods that are used in
all sections of the program, such as the option to quit the program or return to the main menu. Therefore, the exitProgram() or runIntroUI()
method would be protected methods in the superclass that could be accessed from all extending classes.
This would also eliminate a lot of code redundancy.<br />

The code for all the console interfaces is structured similarly. For example, the salient methods that make up the AllCoursesUI and AllTasksUI 
are similar. The salient methods which make up my AllCoursesUI are as follows: runCoursesUI() , displayCoursesMenu(), processCoursesCommand(), invalidCoursesInput().
The salient methods which make up my AllTasksUI are as follows: runAllTasksUI(), displayAllTasksMenu(), processAllTasksCommand(), invalidAllTasksInput(). <br />

While each of the corresponding methods are unique, there is also shared functionality between them.
For example, both processAllTasksCommand() and processCoursesCommand() redirect the user to the MainMenu if the user inputs 'm', and exits the
program is the user inputs 'q'. <br />
To eliminate this redundancy, I would create a processCommand() method in the new abstract ConsoleUI superclass that redirects the user to the 
correct place when 'm' or 'q' is inputted. Then, I could place a call to super.processCommand() in the processAllTasksCommand() and
processCoursesCommand() methods, removing the repetitive code in each method.  <br />

This refactoring would eliminate much redundancy and break up the classes into smaller, more manageable and intuitive chunks of code