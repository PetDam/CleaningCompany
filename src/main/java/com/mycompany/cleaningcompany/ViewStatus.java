package com.mycompany.cleaningcompany;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ViewStatus extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("View Status");
        primaryStage.setResizable(true);

        // Create labels, fields, and button
        Label nameLabel = new Label("Name:");
        Label assignLabel = new Label("Assignment:");
        Label hourLabel = new Label("Hour:");
        Label locationLabel = new Label("Location:");
        Label addressLabel = new Label("Address:");
        Label statusLabel = new Label("Status:");
        ComboBox<String> nameComboBox = new ComboBox<>();
        TextField assignField = new TextField();
        TextField hourField = new TextField();
        TextField locationField = new TextField();
        TextField addressField = new TextField();
        TextField statusField = new TextField();
        Button backButton = new Button("Back");

        // Set fields to read-only
        assignField.setEditable(false);
        hourField.setEditable(false);
        locationField.setEditable(false);
        addressField.setEditable(false);
        statusField.setEditable(false);

        // Create VBox to hold the back button
        VBox buttonsVBox = new VBox(10);
        buttonsVBox.getChildren().addAll(backButton);
        buttonsVBox.setAlignment(Pos.CENTER);

        // Create GridPane and set its properties
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        // Add labels, fields, and button to the GridPane
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameComboBox, 1, 0);
        gridPane.add(assignLabel, 0, 1);
        gridPane.add(assignField, 1, 1);
        gridPane.add(hourLabel, 0, 2);
        gridPane.add(hourField, 1, 2);
        gridPane.add(locationLabel, 0, 3);
        gridPane.add(locationField, 1, 3);
        gridPane.add(addressLabel, 0, 4);
        gridPane.add(addressField, 1, 4);
        gridPane.add(statusLabel, 0, 5);
        gridPane.add(statusField, 1, 5);
        gridPane.add(buttonsVBox, 0, 6, 2, 1);
        GridPane.setHalignment(buttonsVBox, HPos.CENTER);

        // Set styles for labels and button
        nameLabel.setStyle("-fx-text-fill: #191919; -fx-background-color: #e5e5e5;");
        assignLabel.setStyle("-fx-text-fill: #191919; -fx-background-color: #e5e5e5;");
        hourLabel.setStyle("-fx-text-fill: #191919; -fx-background-color: #e5e5e5;");
        locationLabel.setStyle("-fx-text-fill: #191919; -fx-background-color: #e5e5e5;");
        addressLabel.setStyle("-fx-text-fill: #191919; -fx-background-color: #e5e5e5;");
        statusLabel.setStyle("-fx-text-fill: #191919; -fx-background-color: #e5e5e5;");
        backButton.setStyle("-fx-background-color: #e5e5e5; -fx-text-fill: #191919;");

        // Set action for back button
        backButton.setOnAction(event -> {
            Admin.showAdminPage(primaryStage, null);
        });

        // Create StackPane and set its properties
        StackPane stackPane = new StackPane();

        // Add background image to StackPane
        Image backgroundImage = new Image("file:images/admin.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        stackPane.getChildren().add(backgroundImageView);

        // Add GridPane to StackPane
        stackPane.getChildren().add(gridPane);

        // Create scene and set it to the primary stage
        Scene scene = new Scene(stackPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Create translation animation for the background image
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), backgroundImageView);
        translateTransition.setByX(20);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();

        // Load names from file and populate the combo box
        loadNamesFromFile(nameComboBox);

        // Set action for name combo box selection
        nameComboBox.setOnAction(event -> {
            String selectedName = nameComboBox.getSelectionModel().getSelectedItem();
            if (selectedName != null) {
                try (BufferedReader reader = new BufferedReader(new FileReader("data/save/progress.dat"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] fields = line.split(",");
                        if (fields.length >= 6 && fields[0].equals(selectedName)) {
                            assignField.setText(fields[1]);
                            hourField.setText(fields[2]);
                            locationField.setText(fields[3]);
                            addressField.setText(fields[4]);
                            statusField.setText(fields[5]);
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Load names from file and populate the combo box
    private void loadNamesFromFile(ComboBox<String> nameComboBox) {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/save/progress.dat"))) {
            String line;
            ObservableList<String> names = nameComboBox.getItems();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                names.addAll(fields[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
