package com.mycompany.cleaningcompany;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class EmployeeListPage extends Application {
    private static String filePath = "data/save/clients.dat";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee List");

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);

        ListView<String> employeeListView = new ListView<>();
        employeeListView.setPrefHeight(200);

        ArrayList<Employee> employeeList = SystemIOHandler.readEmployeeFromFile(filePath);
        ObservableList<String> employeeNames = FXCollections.observableArrayList();
        for (Employee employee : employeeList) {
            String name = employee.getUsername();
            employeeNames.add(name);
        }
        employeeListView.setItems(employeeNames);

        vbox.getChildren().addAll(new Label("Employee List"), employeeListView);

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
