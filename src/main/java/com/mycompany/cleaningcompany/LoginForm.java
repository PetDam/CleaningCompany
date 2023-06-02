package com.mycompany.cleaningcompany;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

public class LoginForm extends Application {
    private static final String employeeFilePath = "data/save/employees.dat";
    private static final String BACKGROUND_IMAGE_PATH = "file:images/login.jpg";
    private static String username;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Form");

        // Set background image
        Image backgroundImage = new Image(BACKGROUND_IMAGE_PATH);
        ImageView backgroundImageView = new ImageView(backgroundImage);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(backgroundImageView);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(30));
        vbox.setSpacing(15);
        vbox.setAlignment(Pos.CENTER);

        HBox usernameBox = new HBox();
        usernameBox.setSpacing(6);
        usernameBox.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-text-fill: white;");

        TextField usernameField = new TextField();

        usernameBox.getChildren().addAll(usernameLabel, usernameField);

        HBox passwordBox = new HBox();
        passwordBox.setSpacing(10);
        passwordBox.setAlignment(Pos.CENTER);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-text-fill: white;");

        PasswordField passwordField = new PasswordField();

        passwordBox.getChildren().addAll(passwordLabel, passwordField);

        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(10);
        buttonsBox.setAlignment(Pos.CENTER);

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            username = usernameField.getText();
            String password = passwordField.getText();
            isValidCredentials(username, password, primaryStage);
        });

        Button signUpButton = new Button("Sign Up");
        signUpButton.setOnAction(e -> {
            System.out.println("Sign Up button clicked!");
            SignUpForm signUpPage = new SignUpForm();
            signUpPage.start(new Stage());
            primaryStage.close();
        });

        buttonsBox.getChildren().addAll(loginButton);
        vbox.getChildren().addAll(
                usernameBox,
                passwordBox,
                buttonsBox,
                createLabel("Don't Have an Account?", "-fx-text-fill: white;"),
                signUpButton);

        stackPane.getChildren().add(vbox);

        Scene scene = new Scene(stackPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Set background image animation
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), backgroundImageView);
        translateTransition.setByX(20);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }

    // Check if the entered credentials are valid
    private boolean isValidCredentials(String username, String password, Stage primaryStage) {
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Invalid Credentials", "Please enter a username and password.");
            start(new Stage());
            primaryStage.close();
            return false;
        }

        // Read employee data from file
        ArrayList<Employee> employeeList = EmployeeList.readEmployeeFromFile(employeeFilePath);
        boolean validCredentials = false;

        // Check if the entered credentials match an employee or admin account
        for (Employee employee : employeeList) {
            if (employee.getUsername().equals(username) && employee.getPassword().equals(password)) {
                // Open employee page if credentials match an employee
                Employee.showEmployeePage(new Stage(), employee);
                validCredentials = true;
                break;
            } else if (username.equals("admin") && password.equals("admin")) {
                // Open admin page if credentials match admin
                Admin.showAdminPage(new Stage(), employee);
                validCredentials = true;
                break;
            }
        }

        if (!validCredentials) {
            showAlert("Invalid Credentials", "The entered username or password is incorrect.");
            start(new Stage());
            primaryStage.close();
        }

        return validCredentials;
    }

    // Create a label with custom text and style
    private Label createLabel(String text, String style) {
        Label label = new Label(text);
        label.setStyle(style);
        return label;
    }

    // Show an alert with custom title and message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static String getUsername() {
        return username;
    }
}
