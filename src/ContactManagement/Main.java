package ContactManagement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser.ExtensionFilter;

class Contact extends HBox {

    private Label index;
    private TextField contactName;
    private TextField contactPhoneNo;
    private TextField contactAddress;
    private CheckBox selectDelete;
    private Button uploadButton;
    private ImageView contactImageView;
    private FileChooser chooseImage;
    private Stage imageStage;

    Contact() {
        this.setPrefSize(750, 50); // sets size of contact
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of contact

        index = new Label();
        index.setText(""); // create index label
        index.setPrefSize(40, 20); // set size of Index label
        index.setTextAlignment(TextAlignment.CENTER); // Set alignment of index label
        index.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the contact
        this.getChildren().add(index); // add index label to contact

        contactImageView = new ImageView();
        uploadButton = new Button("Upload Image");
        uploadButton.setPrefSize(100, 100);
        uploadButton.setPrefHeight(Double.MAX_VALUE);
        uploadButton.setOnAction(e -> {
            this.uploadImage();
        });
        this.getChildren().add(contactImageView);

        contactName = new TextField(); // create contact name text field
        //TODO: adjust size later
        contactName.setPrefSize(150, 20); // set size of text field
        contactName.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // set background color of texfield
        index.setTextAlignment(TextAlignment.LEFT); // set alignment of text field
        contactName.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.getChildren().add(contactName); // add textlabel to contact

        contactPhoneNo = new TextField();
        contactPhoneNo.setPrefSize(150, 20);
        contactPhoneNo.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // set background color of texfield
        contactPhoneNo.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.getChildren().add(contactPhoneNo);

        contactAddress = new TextField();
        contactAddress.setPrefSize(150, 20);
        contactAddress.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // set background color of texfield
        contactAddress.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.getChildren().add(contactAddress);

        selectDelete = new CheckBox();
        selectDelete.setPrefSize(10, 10);
        selectDelete.setPrefHeight(Double.MAX_VALUE);

        this.getChildren().addAll(selectDelete, uploadButton); //removed edit button
    }

    public void setContactIndex(int num) {
        this.index.setText(num + ""); // num to String
        this.contactName.setPromptText("Contact " + num);
        this.contactPhoneNo.setPromptText("Phone No.");
        this.contactAddress.setPromptText("Address");
    }

    public void setContactPhoneNo() {
        this.contactPhoneNo.setPromptText("Phone No.");
    }

    public void setContactAddress() {
        this.contactAddress.setPromptText("Address");
    }

    public TextField getContactName() {
        return this.contactName;
    }

    public TextField getContactPhoneNo(){
        return this.contactPhoneNo;
    }

    public TextField getContactAddress(){
        return this.contactAddress;
    }

    public CheckBox getDeleteCheckbox() {
        return this.selectDelete;
    }

    public Button getUploadButton() {
        return this.uploadButton;
    }

    public boolean isChecked() {
        return this.selectDelete.isSelected();
    }

    private void uploadImage() {
        chooseImage = new FileChooser();
        chooseImage.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = chooseImage.showOpenDialog(imageStage);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());

            // Resize the window to fit the image
            contactImageView.setImage(image);
            contactImageView.setFitHeight(75);
            contactImageView.setFitWidth(75);
        }
        //VBox imageBox = new VBox(contactImageView);
        //imageBox.setAlignment(Pos.CENTER_LEFT);
    }
}

class ContactList extends VBox {

    ContactList() {
        this.setSpacing(5); // sets spacing between contacts
        this.setPrefSize(750, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
    }

    public void updateContactIndices() {
        int index = 1;
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof Contact) {
                ((Contact) this.getChildren().get(i)).setContactIndex(index);
                index++;
            }
        }
    }

    public void deleteCheckedContacts() {
        this.getChildren().removeIf(contact -> contact instanceof Contact && ((Contact) contact).isChecked());
        this.updateContactIndices();
    }

    //TODO: save the contacts to a CSV file
    public void saveContacts() {
        // hint 1: use try-catch block
        // hint 2: use FileWriter
        // hint 3: this.getChildren() gets the list of tasks
        try {
            FileWriter writeContact = new FileWriter("contacts.csv", false);
            writeContact.write("Contact Name, " + "Phone Number, " + "Address\n");
            for (int i = 0; i < this.getChildren().size(); i++) {
                if (this.getChildren().get(i) instanceof Contact) {
                    Contact contact = (Contact) this.getChildren().get(i);
                    //getTaskName().getText() gives the task name
                    writeContact.write(contact.getContactName().getText() + ", ");
                    writeContact.write(contact.getContactPhoneNo().getText() + ", ");
                    writeContact.write(contact.getContactAddress().getText() + "\n");
                }
            }
            writeContact.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    // TODO: Sort contacts 
    public void sortContacts() {
        // hint 1: this.getChildren() gets the list of tasks
        // hint 2: Collections.sort() can be used to sort the tasks
        // hint 3: task.getTaskName().setText() sets the text of the task
        ArrayList<Contact> contactList = new ArrayList<Contact>();
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof Contact) {
                contactList.add((Contact)this.getChildren().get(i));
            }
        }
        /*
         *  code generated from chatGPT 3.5 using the prompt 
         *  sort tasks in a to-do-list lexicographically in java
         */ 
        Collections.sort(contactList, new Comparator<Contact>() {
            public int compare(Contact contact1, Contact contact2) {
                String contactString1 = contact1.getContactName().getText();
                String contactString2 = contact2.getContactName().getText();
                return contactString1.compareToIgnoreCase(contactString2);
            }
        });
        this.getChildren().setAll(contactList);
        updateContactIndices();
    }
}

class Footer extends HBox {

    private Button addButton;
    private Button saveButton;
    private Button sortButton;
    private Button deleteButton;


    Footer() {
        this.setPrefSize(750, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);

        // set a default style for buttons - background color, font size, italics
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        addButton = new Button("Add Contact"); // text displayed on add button
        addButton.setStyle(defaultButtonStyle); // styling the button

        this.getChildren().addAll(addButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center

        saveButton = new Button("Save Contacts");
        saveButton.setStyle(defaultButtonStyle);
        sortButton = new Button("Sort Contacts (By Name)");
        sortButton.setStyle(defaultButtonStyle);
        deleteButton = new Button("Delete Checked Contacts");
        deleteButton.setStyle(defaultButtonStyle);
        this.getChildren().addAll(saveButton, sortButton, deleteButton);
        this.setAlignment(Pos.CENTER);
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getSortButton() {
        return sortButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

}

class Header extends HBox {

    Header() {
        this.setPrefSize(750, 60); // Size of the header
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
    private ContactList contactList;
    private Button addButton;
    private Button saveButton;
    private Button sortButton;
    private Button deleteButton;

    AppFrame()
    {
        // Initialise the header Object
        header = new Header();

        // Create a tasklist Object to hold the tasks
        contactList = new ContactList();
        
        // Initialise the Footer Object
        footer = new Footer();

        ScrollPane scroller = new ScrollPane(contactList);
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
        saveButton = footer.getSaveButton();
        sortButton = footer.getSortButton();
        deleteButton = footer.getDeleteButton();

        // Call Event Listeners for the Buttons
        addListeners();
    }

    public void addListeners()
    {

        // Add button functionality
        addButton.setOnAction(e -> {
            // Create a new task
            Contact contact = new Contact();
            // Add task to tasklist
            contactList.getChildren().add(contact);
            
            // Update task indices
            contactList.updateContactIndices();
        });
        
        deleteButton.setOnAction(e -> {
            contactList.deleteCheckedContacts();
        });

        saveButton.setOnAction(e -> {
            contactList.saveContacts();
        });

        sortButton.setOnAction(e -> {
            contactList.sortContacts();
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
        primaryStage.setScene(new Scene(root, 750, 600));
        // Make window non-resizable
        primaryStage.setResizable(false);
        // Show the app
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
