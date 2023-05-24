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
    private static final String userFilePath = "data/save/users.dat";
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
            String username = usernameField.getText();
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

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), backgroundImageView);
        translateTransition.setByX(20);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }

    private boolean isValidCredentials(String username, String password, Stage primaryStage) {
        ArrayList<User> userList = UserList.readUserFromFile(userFilePath);
        boolean isAdmin = false;

        for (User user : userList) {
            if (user instanceof Employee && user.getUsername().equals(username) && user.getPassword().equals(password)) {
                Employee.showEmployeePage(new Stage(), (Employee) user);
                return true;
            } else if (user instanceof Admin && user.getUsername().equals("admin") && user.getPassword().equals("")) {
                Admin.showAdminPage(new Stage(), user);
                return true;
            } else {
                showAlert("Invalid Credentials", "The entered username or password is incorrect.");
                return false;
            } 
        }

        if (!isAdmin) {
            showAlert("Invalid Credentials", "The entered username or password is incorrect.");
        }

        return false;
    }

    private Label createLabel(String text, String style) {
        Label label = new Label(text);
        label.setStyle(style);
        return label;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
