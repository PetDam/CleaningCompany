package com.mycompany.cleaningcompany;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginForm extends Application {
    private static String filePath = "data/save/clients.dat";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Form");

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setPrefWidth(80);

        TextField usernameField = new TextField();
        usernameField.setPrefWidth(150);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setPrefWidth(80);

        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(150);

        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(10);

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (isValidCredentials(username, password)) {
                System.out.println("Login successful!");
                EmployeeList.addEmployees();
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
            usernameField.clear();
            passwordField.clear();
        });
        Button signUpButton = new Button("Sign Up");
        signUpButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            System.out.println("Sign Up button clicked!");
            usernameField.clear();
            passwordField.clear();
        });

        Label signUpLabel = new Label("Don't Have an Account?");
        signUpLabel.setPadding(new Insets(0, 0, 0, 5));

        buttonsBox.getChildren().addAll(loginButton);
        vbox.getChildren().addAll(
                new HBox(usernameLabel, usernameField),
                new HBox(passwordLabel, passwordField),
                buttonsBox,
                new HBox(signUpLabel, signUpButton)
        );
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean isValidCredentials(String username, String password) {
        // Εδώ προσθέστε τη λογική επαλήθευσης των διαπιστευτηρίων
        return true;
    }
}
