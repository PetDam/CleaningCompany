package com.mycompany.cleaningcompany;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SignUpForm extends Application {
    private static final String userFilePath = "data/save/users.dat";
    private static final ArrayList<User> userList = UserList.readUserFromFile(userFilePath);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sign Up Form");

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button signUpButton = new Button("Sign Up");
        signUpButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            User newUser = new User(username, password);
            userList.add(newUser);
            UserList.storeUserInFile(userList, userFilePath);
            System.out.println("Sign Up successful!");
            usernameField.clear();
            passwordField.clear();
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            LoginForm loginForm = new LoginForm();
            loginForm.start(primaryStage);
        });

        vbox.getChildren().addAll(
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                signUpButton,
                backButton);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
