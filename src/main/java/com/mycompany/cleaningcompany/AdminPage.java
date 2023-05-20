package com.mycompany.cleaningcompany;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        Button button1 = new Button("Add Assignment");
        Button button2 = new Button("View Status");
        Button button3 = new Button("Button 3");

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(button1, button2, button3);

        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("Admin's Page ");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
