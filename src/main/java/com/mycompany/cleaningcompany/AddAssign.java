package com.mycompany.cleaningcompany;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AddAssign extends Application {
    private static final String ASSIGNMENTS_FILE_PATH = "data/save/assignments.dat";

    // List that holds employees
    public static final ArrayList<Employee> employeeList = SystemIOHandler
            .readEmployeeFromFile("data/save/employees.dat");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Add Assignment");
        primaryStage.setResizable(true);

        // Creating UI elements
        Label userLabel = new Label("Select User:");
        ComboBox<String> employeeComboBox = new ComboBox<>();
        employeeComboBox.setPromptText("Click Here:");
        Label titleLabel = new Label("Add Assignment:");
        Label hourLabel = new Label("Hour:");
        Label locationLabel = new Label("Location:");
        Label addressLabel = new Label("Address:");
        TextField assignmentTextArea = new TextField();
        TextField hourField = new TextField();
        TextField locationField = new TextField();
        TextField addressField = new TextField();
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");

        // List of employee names for display in the ComboBox
        ObservableList<String> employeeNames = FXCollections.observableArrayList();
        for (Employee employee : employeeList) {
            employeeNames.add(employee.getUsername());
        }
        employeeComboBox.setItems(employeeNames);

        // Creating the form layout using GridPane
        VBox buttonsVBox = new VBox(10);
        buttonsVBox.getChildren().addAll(submitButton, backButton);
        buttonsVBox.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        gridPane.add(userLabel, 0, 0);
        gridPane.add(employeeComboBox, 1, 0);
        gridPane.add(titleLabel, 0, 1);
        gridPane.add(assignmentTextArea, 1, 1);
        gridPane.add(hourLabel, 0, 2);
        gridPane.add(hourField, 1, 2);
        gridPane.add(locationLabel, 0, 3);
        gridPane.add(locationField, 1, 3);
        gridPane.add(addressLabel, 0, 4);
        gridPane.add(addressField, 1, 4);
        gridPane.add(buttonsVBox, 0, 5, 2, 1);
        GridPane.setHalignment(buttonsVBox, HPos.CENTER);

        userLabel.setStyle("-fx-text-fill: black;");
        titleLabel.setStyle("-fx-text-fill: black;");
        hourLabel.setStyle("-fx-text-fill: black;");
        locationLabel.setStyle("-fx-text-fill: black;");
        addressLabel.setStyle("-fx-text-fill: black;");

        submitButton.setStyle("-fx-background-color: #c6bde1; -fx-text-fill: #473e61;");
        submitButton.setOnAction(event -> {
            String selectedUser = employeeComboBox.getValue();
            String assignment = assignmentTextArea.getText();
            String hour = hourField.getText();
            String location = locationField.getText();
            String address = addressField.getText();

            boolean isNameOnly = selectedUser != null && assignment.isEmpty() && hour.isEmpty() && location.isEmpty()
                    && address.isEmpty();

            if (isNameOnly) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in the other fields!");
                alert.showAndWait();
            } else if (selectedUser == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select a user!");
                alert.showAndWait();
            } else if (assignment.isEmpty() || hour.isEmpty() || location.isEmpty() || address.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all the fields!");
                alert.showAndWait();
            } else {
                assignWork(selectedUser, assignment, hour, location, address);
                saveAssignment(selectedUser, assignment, hour, location, address);

                // Clearing text fields
                assignmentTextArea.clear();
                hourField.clear();
                locationField.clear();
                addressField.clear();

                // Displaying success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Assignment added successfully!");
                alert.showAndWait();
            }
        });

        backButton.setStyle("-fx-background-color: #c6bde1; -fx-text-fill: #473e61;");
        backButton.setOnAction(event -> {
            Admin.showAdminPage(primaryStage, null);
        });

        // Creating a StackPane to hold the background image and form layout
        StackPane stackPane = new StackPane();

        Image backgroundImage = new Image("file:images/admin.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        stackPane.getChildren().add(backgroundImageView);

        stackPane.getChildren().add(gridPane);

        // Creating the scene and setting it on the stage
        Scene scene = new Scene(stackPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Creating a TranslateTransition for the background image
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), backgroundImageView);
        translateTransition.setByX(20);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }

    // Method to assign work to an employee
    private void assignWork(String employeeName, String assignment, String hour, String location, String address) {
        Employee selectedEmployee = null;
        for (Employee employee : employeeList) {
            if (employee.getUsername().equals(employeeName)) {
                selectedEmployee = employee;
                break;
            }
        }
        if (selectedEmployee != null) {
            selectedEmployee.setAssignWork(assignment);
            System.out.println("Work assigned to user: " + selectedEmployee.getUsername());
            System.out.println("Assign Work: " + assignment);
            System.out.println("Hour: " + hour);
            System.out.println("Location: " + location);
            System.out.println("Address: " + address);
        } else {
            System.out.println("Invalid user type");
        }
    }

    // Method to save the assignment to a file
    private void saveAssignment(String employeeName, String assignment, String hour, String location, String address) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ASSIGNMENTS_FILE_PATH, true))) {
            String data = employeeName + "," + assignment + "," + hour + "," + location + "," + address;
            writer.write(data);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}