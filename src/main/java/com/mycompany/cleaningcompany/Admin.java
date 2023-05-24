package com.mycompany.cleaningcompany;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password);
    }

    public static void showAdminPage(Stage primaryStage, User user) {
        primaryStage.setTitle("Admin's Page");

        Button button1 = new Button("Add Assignment");
        Button button2 = new Button("View Status");
        Button button3 = new Button("Button 3");

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(button1, button2, button3);

        Scene scene = new Scene(root, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
