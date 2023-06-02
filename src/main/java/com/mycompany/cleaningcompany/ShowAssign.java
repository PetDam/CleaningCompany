package com.mycompany.cleaningcompany;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

public class ShowAssign extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Show Your Assignment");
        primaryStage.setResizable(true);

        // Labels
        Label nameLabel = new Label("Your Name:");
        Label assignLabel = new Label("Your Assignment:");
        Label hourLabel = new Label("Hour:");
        Label locationLabel = new Label("Location:");
        Label addressLabel = new Label("Address:");
        Label statusLabel = new Label("Status:");

        // TextFields
        TextField nameField = new TextField();
        TextField assignField = new TextField();
        TextField hourField = new TextField();
        TextField locationField = new TextField();
        TextField addressField = new TextField();

        // ComboBox
        ComboBox<String> statusComboBox = new ComboBox<>();

        // Buttons
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");

        // Set TextFields as non-editable
        nameField.setEditable(false);
        assignField.setEditable(false);
        hourField.setEditable(false);
        locationField.setEditable(false);
        addressField.setEditable(false);

        // Create a VBox for buttons
        VBox buttonsVBox = new VBox(10);
        buttonsVBox.getChildren().addAll(submitButton, backButton);
        buttonsVBox.setAlignment(Pos.CENTER);

        // Create a GridPane for layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        // Add labels, fields, and buttons to the gridPane
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(assignLabel, 0, 1);
        gridPane.add(assignField, 1, 1);
        gridPane.add(hourLabel, 0, 2);
        gridPane.add(hourField, 1, 2);
        gridPane.add(locationLabel, 0, 3);
        gridPane.add(locationField, 1, 3);
        gridPane.add(addressLabel, 0, 4);
        gridPane.add(addressField, 1, 4);
        gridPane.add(statusLabel, 0, 5);
        gridPane.add(statusComboBox, 1, 5);
        gridPane.add(buttonsVBox, 0, 6, 2, 1);
        GridPane.setHalignment(buttonsVBox, HPos.CENTER);

        // Set styles for labels
        nameLabel.setStyle("-fx-text-fill: #191919; -fx-background-color: #e5e5e5;");
        assignLabel.setStyle("-fx-text-fill: #191919; -fx-background-color: #e5e5e5;");
        hourLabel.setStyle("-fx-text-fill: #191919; -fx-background-color: #e5e5e5;");
        locationLabel.setStyle("-fx-text-fill: #191919; -fx-background-color: #e5e5e5;");
        addressLabel.setStyle("-fx-text-fill: #191919; -fx-background-color: #e5e5e5;");
        statusLabel.setStyle("-fx-text-fill: #191919; -fx-background-color: #e5e5e5;");

        // Set style for the back button
        backButton.setStyle("-fx-background-color: #e5e5e5; -fx-text-fill: #191919;");

        // Set action for the back button
        backButton.setOnAction(event -> {
            Employee.showEmployeePage(primaryStage, null);
        });

        // Create a StackPane for the background image and gridPane
        StackPane stackPane = new StackPane();

        // Add background image to stackPane
        Image backgroundImage = new Image("file:images/employee.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        stackPane.getChildren().add(backgroundImageView);

        // Add gridPane to stackPane
        stackPane.getChildren().add(gridPane);

        // Create a scene with the stackPane
        Scene scene = new Scene(stackPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add animation to the background image
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), backgroundImageView);
        translateTransition.setByX(20);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();

        // Set the nameField with the username from the LoginForm
        nameField.setText(LoginForm.getUsername());

        // Load assignments from file and populate the fields and comboBox
        loadAssignmentsFromFile(assignField, hourField, locationField, addressField, statusComboBox);

        // Set options for the status comboBox
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Done", "In Progress");
        statusComboBox.setItems(statusOptions);

        // Set action for the submit button
        submitButton.setOnAction(event -> {
            boolean success = saveProgress(nameField.getText(), assignField.getText(), hourField.getText(),
                    locationField.getText(), addressField.getText(), statusComboBox.getValue());
            if (success) {
                showSuccessAlert();
            } else if (statusComboBox.getValue() == null) {
                showStatusAlert();
            }
        });
    }

    // Load assignments from file and populate the fields and comboBox
    private void loadAssignmentsFromFile(TextField assignField, TextField hourField, TextField locationField,
            TextField addressField, ComboBox<String> statusComboBox) {
        String username = LoginForm.getUsername();
        try (BufferedReader reader = new BufferedReader(new FileReader("data/save/assignments.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 5 && fields[0].equals(username)) {
                    assignField.setText(fields[1]);
                    hourField.setText(fields[2]);
                    locationField.setText(fields[3]);
                    addressField.setText(fields[4]);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("There's no assignment for you yet.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading assignments: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Save the progress to a file
    private boolean saveProgress(String name, String assignment, String hour, String location, String address,
            String status) {
        if (status == null) {
            return false;
        }

        String progress = name + "," + assignment + "," + hour + "," + location + "," + address + "," + status;

        try (BufferedReader reader = new BufferedReader(new FileReader("data/save/progress.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 5 && fields[1].equals(assignment) && fields[2].equals(hour)
                        && fields[3].equals(location) && fields[4].equals(address)) {
                    showDuplicateAlert();
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Successful Storage!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/save/progress.dat", true))) {
            writer.write(progress);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Show an alert for duplicate entry
    private void showDuplicateAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Duplicate Entry");
        alert.setHeaderText(null);
        alert.setContentText("An entry with the same details already exists.");
        alert.showAndWait();
    }

    // Show a success alert
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Progress saved successfully!");
        alert.showAndWait();
    }

    // Show an alert for missing status
    private void showStatusAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please select a status value!");
        alert.showAndWait();
    }

}
