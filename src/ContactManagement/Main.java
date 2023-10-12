package ContactManagement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Task extends HBox {

    private Label index;
    private TextField taskName;
    private Button deleteButton;
    private Button saveButton;
    private Button editButton;

    private boolean markedDone;

    Task() {
        this.setPrefSize(500, 20); // sets size of task
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of task
        markedDone = false;

        index = new Label();
        index.setText(""); // create index label
        index.setPrefSize(40, 20); // set size of Index label
        index.setTextAlignment(TextAlignment.CENTER); // Set alignment of index label
        index.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the task
        this.getChildren().add(index); // add index label to task

        taskName = new TextField(); // create task name text field
        taskName.setPrefSize(380, 20); // set size of text field
        taskName.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // set background color of texfield
        index.setTextAlignment(TextAlignment.LEFT); // set alignment of text field
        taskName.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.getChildren().add(taskName); // add textlabel to task

        deleteButton = new Button("Delete"); // creates a button for marking the task as done
        deleteButton.setPrefSize(100, 20);
        deleteButton.setPrefHeight(Double.MAX_VALUE);
        deleteButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button

        saveButton = new Button("Save"); // creates a button for marking the task as done
        saveButton.setPrefSize(100, 20);
        saveButton.setPrefHeight(Double.MAX_VALUE);
        saveButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button

        editButton = new Button("Edit"); // creates a button for marking the task as done
        editButton.setPrefSize(100, 20);
        editButton.setPrefHeight(Double.MAX_VALUE);
        editButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button

        this.getChildren().addAll(editButton, deleteButton, saveButton);
    }

    public void setTaskIndex(int num) {
        this.index.setText(num + ""); // num to String
        this.taskName.setPromptText("Contact " + num);
    }

    public TextField getTaskName() {
        return this.taskName;
    }

    public Button getDoneButton() {
        return this.deleteButton;
    }

    public boolean isMarkedDone() {
        return this.markedDone;
    }

    /*
     * Used ChatGPT 3.5 to generate code structure using this prompt:
     * one button that does one action, and then when the same button is pressed again a different action happens
     */
    public void toggleDone() {
        if(!markedDone) {
            this.setStyle("-fx-border-color: #000000; -fx-border-width: 0; -fx-font-weight: bold;"); // remove border of task
            for (int i = 0; i < this.getChildren().size(); i++) {
                this.getChildren().get(i).setStyle("-fx-background-color: #BCE29E; -fx-border-width: 0;"); // change color of task to green
            }
        }
        else {
            this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // adds border back
            for (int i = 0; i < this.getChildren().size(); i++) {
                this.getChildren().get(i).setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // resets color of task to gray
        }
        }
        markedDone = !markedDone;
        
    }
}

class TaskList extends VBox {

    TaskList() {
        this.setSpacing(5); // sets spacing between tasks
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
    }

    public void updateTaskIndices() {
        int index = 1;
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof Task) {
                ((Task) this.getChildren().get(i)).setTaskIndex(index);
                index++;
            }
        }
    }

    public void removeCompletedTasks() {
        this.getChildren().removeIf(task -> task instanceof Task && ((Task) task).isMarkedDone());
        this.updateTaskIndices();
    }

    // TODO: Complete this method
    /*
     * Load tasks from a file called "tasks.txt"
     * Add the tasks to the children of tasklist component
     */
    public void loadTasks() {
        // hint 1: use try-catch block
        // hint 2: use BufferedReader and FileReader
        // hint 3: task.getTaskName().setText() sets the text of the task
        try {
            BufferedReader input = new BufferedReader(new FileReader("tasks.txt"));
            String text = input.readLine();
            // while input is not at end of file
            while (text != null) {
                Task task = new Task();
                task.getTaskName().setText(text);
                this.getChildren().add(task);
                text = input.readLine();
            }
            input.close(); 
            updateTaskIndices();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: Complete this method
    /*
     * Save tasks to a file called "tasks.txt"
     */
    public void saveTasks() {
        // hint 1: use try-catch block
        // hint 2: use FileWriter
        // hint 3: this.getChildren() gets the list of tasks
        try {
            FileWriter writeTask = new FileWriter("tasks.txt", false);
            for (int i = 0; i < this.getChildren().size(); i++) {
                if (this.getChildren().get(i) instanceof Task) {
                    Task task = (Task) this.getChildren().get(i);
                    //getTaskName().getText() gives the task name
                    writeTask.write(task.getTaskName().getText() + "\n");
                }
            }
            writeTask.close();
        /* Bugs/Issues:
         * toString turns the node into a string representation of the memory address
         * loadTasks only loads one task, big problem when saving more than one task
         * Have not finished sortTasks
         * Need to adjust loadTasks action 
         * to provide proper Done button functionality from exercise 2
         */
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    // TODO: Complete this method
    /*
     * Sort the tasks lexicographically
     */
    public void sortTasks() {
        // hint 1: this.getChildren() gets the list of tasks
        // hint 2: Collections.sort() can be used to sort the tasks
        // hint 3: task.getTaskName().setText() sets the text of the task
        ArrayList<Task> taskList = new ArrayList<Task>();
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof Task) {
                taskList.add((Task)this.getChildren().get(i));
            }
        }
        /*
         *  code generated from chatGPT 3.5 using the prompt 
         *  sort tasks in a to-do-list lexicographically in java
         */ 
        Collections.sort(taskList, new Comparator<Task>() {
            public int compare(Task task1, Task task2) {
                String taskString1 = task1.getTaskName().getText();
                String taskString2 = task2.getTaskName().getText();
                return taskString1.compareToIgnoreCase(taskString2);
            }
        });
        this.getChildren().setAll(taskList);
        updateTaskIndices();
    }
}

class Footer extends HBox {

    private Button addButton;
    //private Button clearButton;
    // TODO: Add a button called "loadButton" to load tasks from file
    // TODO: Add a button called "saveButton" to save tasks to a file
    // TODO: Add a button called "sortButton" to sort the tasks lexicographically
    //private Button loadButton;
    private Button saveButton;
    private Button sortButton;


    Footer() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);

        // set a default style for buttons - background color, font size, italics
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        addButton = new Button("Add Contact"); // text displayed on add button
        addButton.setStyle(defaultButtonStyle); // styling the button
        /* 
        clearButton = new Button("Clear finished"); // text displayed on clear tasks button
        clearButton.setStyle(defaultButtonStyle);
        */
        this.getChildren().addAll(addButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center

        // TODO: Create loadButton, saveButton and sortButton to the footer
        /* 
        loadButton = new Button("Load Tasks");
        loadButton.setStyle(defaultButtonStyle);
        */
        saveButton = new Button("Save Contacts");
        saveButton.setStyle(defaultButtonStyle);
        sortButton = new Button("Sort Contacts (By Name)");
        sortButton.setStyle(defaultButtonStyle);
        this.getChildren().addAll(saveButton, sortButton);
        this.setAlignment(Pos.CENTER);
    }

    public Button getAddButton() {
        return addButton;
    }
    /* 
    public Button getClearButton() {
        return clearButton;
    }

    public Button getLoadButton() {
        return loadButton;
    }
    */
    public Button getSaveButton() {
        return saveButton;
    }

    public Button getSortButton() {
        return sortButton;
    }

    // TODO: Add getters for loadButton, saveButton and sortButton
}

class Header extends HBox {

    Header() {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");

        Text titleText = new Text("CONTACTS"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }
}

class AppFrame extends BorderPane{

    private Header header;
    private Footer footer;
    private TaskList taskList;

    private Button addButton;
    //private Button clearButton;
    //private Button loadButton;
    private Button saveButton;
    private Button sortButton;

    AppFrame()
    {
        // Initialise the header Object
        header = new Header();

        // Create a tasklist Object to hold the tasks
        taskList = new TaskList();
        
        // Initialise the Footer Object
        footer = new Footer();

        // TODO: Add a Scroller to the Task List
        // hint 1: ScrollPane() is the Pane Layout used to add a scroller - it will take the tasklist as a parameter
        // hint 2: setFitToWidth, and setFitToHeight attributes are used for setting width and height
        // hint 3: The center of the AppFrame layout should be the scroller window instead  of tasklist
        ScrollPane scroller = new ScrollPane(taskList);
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);

        // Add header to the top of the BorderPane
        this.setTop(header);
        // Add scroller to the centre of the BorderPane
        this.setCenter(scroller);
        // Add footer to the bottom of the BorderPane
        this.setBottom(footer);

        // Initialise Button Variables through the getters in Footer
        addButton = footer.getAddButton();
        //clearButton = footer.getClearButton();
        //loadButton = footer.getLoadButton();
        saveButton = footer.getSaveButton();
        sortButton = footer.getSortButton();

        // Call Event Listeners for the Buttons
        addListeners();
    }

    public void addListeners()
    {

        // Add button functionality
        addButton.setOnAction(e -> {
            // Create a new task
            Task task = new Task();
            // Add task to tasklist
            taskList.getChildren().add(task);
            // Add doneButtonToggle to the Done button
            Button doneButton = task.getDoneButton();
            doneButton.setOnAction(e1 -> task.toggleDone());
            
            // Update task indices
            taskList.updateTaskIndices();
        });
        
        // Clear finished tasks
        /* 
        clearButton.setOnAction(e -> {
            taskList.removeCompletedTasks();
        });

        loadButton.setOnAction(e -> {
            taskList.loadTasks();
            for (int i = 0; i < taskList.getChildren().size(); i++) {
                if (taskList.getChildren().get(i) instanceof Task) {
                    Task task = (Task) taskList.getChildren().get(i);
                    Button doneButton = task.getDoneButton();
                    doneButton.setOnAction(e1 -> task.toggleDone());
                }
            }
        });
        */
        saveButton.setOnAction(e -> {
            taskList.saveTasks();
        });

        sortButton.setOnAction(e -> {
            taskList.sortTasks();
        });
    }
}

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setting the Layout of the Window- Should contain a Header, Footer and the TaskList
        AppFrame root = new AppFrame();

        // Set the title of the app
        primaryStage.setTitle("CONTACTS");
        // Create scene of mentioned size with the border pane
        primaryStage.setScene(new Scene(root, 500, 600));
        // Make window non-resizable
        primaryStage.setResizable(false);
        // Show the app
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
