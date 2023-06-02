package com.mycompany.cleaningcompany;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password);
    }

    public static void showAdminPage(Stage primaryStage, User user) {
        primaryStage.setTitle("Admin's Page");

        // Set background image
        Image backgroundImage = new Image("file:images/admin.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(backgroundImageView);

        // Create buttons
        Button button1 = new Button("View Employees");
        Button button2 = new Button("Add Assignment");
        Button button3 = new Button("View Status");

        // Set styles for buttons
        button1.setStyle("-fx-background-color: #c6bde1; -fx-text-fill: #473e61;");
        button2.setStyle("-fx-background-color: #c6bde1; -fx-text-fill: #473e61;");
        button3.setStyle("-fx-background-color: #c6bde1; -fx-text-fill: #473e61;");

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(button1, button2, button3);

        // Button actions
        button1.setOnAction(e -> {
            ViewEmployees.showEmployeePage(primaryStage, user);
        });

        button2.setOnAction(e -> {
            AddAssign addAssign = new AddAssign();
            addAssign.start(primaryStage);
        });

        button3.setOnAction(e -> {
            ViewStatus viewStatus = new ViewStatus();
            viewStatus.start(primaryStage);
        });

        // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #c6bde1; -fx-text-fill: #473e61;");
        logoutButton.setOnAction(e -> {
            LoginForm loginForm = new LoginForm();
            loginForm.start(primaryStage);
        });

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(root, logoutButton);

        stackPane.getChildren().add(vbox);

        Scene scene = new Scene(stackPane, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();

        // Create a TranslateTransition for the background image
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), backgroundImageView);
        translateTransition.setByX(20);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }
}
