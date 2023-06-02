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

public class Employee extends User {
    private int employeeID;
    private static int currentEmployeeId = 1;
    private String assignWork;
    private String hour;
    private String location;
    private String address;
    private static final String filePath = "data/save/employees.dat";

    // Constructor
    public Employee(String username, String password) {
        super(username, password);
        this.employeeID = getNextEmployeeId();
    }

    // Getter and Setter methods
    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public static int getNextEmployeeId() {
        int ID = currentEmployeeId;
        currentEmployeeId++;
        return ID;
    }

    public String getAssignWork() {
        return assignWork;
    }

    public void setAssignWork(String assignWork) {
        this.assignWork = assignWork;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Show the Employee's page
    public static void showEmployeePage(Stage primaryStage, User user) {
        primaryStage.setTitle("Employee's Page");

        // Load and set the background image
        Image backgroundImage = new Image("file:images/employee.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(backgroundImageView);

        // Create a button for showing assignments
        Button button1 = new Button("Show Your Assignment");

        button1.setStyle("-fx-background-color: #f3f1f9; -fx-text-fill: #473e61;");

        // Create a VBox to hold the button
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(button1);

        // Set action for the button
        button1.setOnAction(e -> {
            ShowAssign showAssignPage = new ShowAssign();
            showAssignPage.start(primaryStage);
        });

        // Create a logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #f3f1f9; -fx-text-fill: #473e61;");
        logoutButton.setOnAction(e -> {
            LoginForm loginForm = new LoginForm();
            loginForm.start(primaryStage);
        });

        // Create a VBox to hold the button and the logout button
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(root, logoutButton);

        stackPane.getChildren().add(vbox);

        // Create the scene and set it to the stage
        Scene scene = new Scene(stackPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Apply animation to the background image
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), backgroundImageView);
        translateTransition.setByX(20);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }
}
