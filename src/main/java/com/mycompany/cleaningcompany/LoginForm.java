package com.mycompany.cleaningcompany;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

public class LoginForm extends Application {
    private static final String filePath = "data/save/clients.dat";
    private static final String BACKGROUND_IMAGE_PATH = "file:images/login.jpg";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Form");

        // Load the background image
        Image backgroundImage = new Image(BACKGROUND_IMAGE_PATH);
        ImageView backgroundImageView = new ImageView(backgroundImage);

        // Create the StackPane for the background image
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(backgroundImageView);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setPrefWidth(80);
        usernameLabel.setStyle("-fx-text-fill: white;");

        TextField usernameField = new TextField();
        usernameField.setPrefWidth(150);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setPrefWidth(80);
        passwordLabel.setStyle("-fx-text-fill: white;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(150);

        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(10);
        buttonsBox.setAlignment(Pos.CENTER);

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

        buttonsBox.getChildren().addAll(loginButton);
        vbox.getChildren().addAll(
                new HBox(usernameLabel, usernameField),
                new HBox(passwordLabel, passwordField),
                buttonsBox,
                createLabel("Don't Have an Account?", "-fx-text-fill: white;"),
                signUpButton
        );

        StackPane rootPane = new StackPane(stackPane, vbox);

        Scene scene = new Scene(rootPane, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Move the background image
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(20), stackPane);
        translateTransition.setByX(200);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }

    private boolean isValidCredentials(String username, String password) {
        // Code to check credentials against the file
        return true;
    }

    private Label createLabel(String text, String style) {
        Label label = new Label(text);
        label.setStyle(style);
        return label;
    }
}
