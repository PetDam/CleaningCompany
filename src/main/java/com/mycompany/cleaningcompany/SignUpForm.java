package com.mycompany.cleaningcompany;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class SignUpForm extends Application {
    private static final String filePath = "data/save/employees.dat";
    private static final String adminFilePath = "data/save/admin.dat";
    private static final ArrayList<Employee> employeeList = SystemIOHandler.readEmployeeFromFile(filePath);
    private static final ArrayList<Employee> adminList = SystemIOHandler.readEmployeeFromFile(adminFilePath);
    private static final String BACKGROUND_IMAGE_PATH = "file:images/login.jpg";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sign Up Form");

        // Create background image view
        Image backgroundImage = new Image(BACKGROUND_IMAGE_PATH);
        ImageView backgroundImageView = new ImageView(backgroundImage);

        // Create stack pane and add background image view
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(backgroundImageView);

        // Create vertical box for form elements
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(30));
        vbox.setSpacing(15);
        vbox.setAlignment(Pos.CENTER);

        // Create horizontal box for username field
        HBox usernameBox = new HBox();
        usernameBox.setSpacing(6);
        usernameBox.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-text-fill: white;");

        TextField usernameField = new TextField();

        usernameBox.getChildren().addAll(usernameLabel, usernameField);

        // Create horizontal box for password field
        HBox passwordBox = new HBox();
        passwordBox.setSpacing(10);
        passwordBox.setAlignment(Pos.CENTER);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-text-fill: white;");

        PasswordField passwordField = new PasswordField();

        passwordBox.getChildren().addAll(passwordLabel, passwordField);

        // Create horizontal box for admin checkbox
        HBox adminBox = new HBox();
        adminBox.setSpacing(10);
        adminBox.setAlignment(Pos.CENTER);

        CheckBox adminCheckBox = new CheckBox("Are you an admin?");
        adminCheckBox.setStyle("-fx-text-fill: white;");

        adminBox.getChildren().addAll(adminCheckBox);

        // Create horizontal box for buttons
        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(10);
        buttonsBox.setAlignment(Pos.CENTER);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            LoginForm loginForm = new LoginForm();
            loginForm.start(primaryStage);
        });

        Button signUpButton = new Button("Sign Up");
        signUpButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            boolean isAdmin = adminCheckBox.isSelected();

            int nextId = getNextAvailableId();

            Employee newEmployee = new Employee(username, password);
            newEmployee.setEmployeeID(nextId);

            if (isAdmin) {
                adminList.add(newEmployee);
                SystemIOHandler.storeEmployeeInFile(adminList, adminFilePath);
            } else {
                employeeList.add(newEmployee);
                SystemIOHandler.storeEmployeeInFile(employeeList, filePath);
            }

            System.out.println("Sign Up successful!");
            usernameField.clear();
            passwordField.clear();
            adminCheckBox.setSelected(false);
        });

        buttonsBox.getChildren().addAll(backButton);
        vbox.getChildren().addAll(
                usernameBox,
                passwordBox,
                adminBox,
                signUpButton,
                buttonsBox);

        // Add vbox to stack pane
        stackPane.getChildren().add(vbox);

        // Create scene and set it to the primary stage
        Scene scene = new Scene(stackPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Create translate transition for background image animation
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), backgroundImageView);
        translateTransition.setByX(20);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }

    private int getNextAvailableId() {
        int maxId = 0;

        for (Employee employee : employeeList) {
            if (employee.getEmployeeID() > maxId) {
                maxId = employee.getEmployeeID();
            }
        }
        return maxId + 1;
    }
}