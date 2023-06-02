package com.mycompany.cleaningcompany;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewEmployees {
    private static final String filePath = "data/save/employees.dat";

    // Method to display the employee page
    public static void showEmployeePage(Stage primaryStage, User user) {
        primaryStage.setTitle("View Employees");

        // Read employee data from file
        ArrayList<Employee> employeeList = SystemIOHandler.readEmployeeFromFile(filePath);

        TableView<Employee> table = new TableView<>();

        // Create ID column
        TableColumn<Employee, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        idColumn.setStyle("-fx-padding: 10px;");
        idColumn.setPrefWidth(150);
        table.getColumns().add(idColumn);

        // Create username column
        TableColumn<Employee, String> usernameColumn = new TableColumn<>("Name");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameColumn.setStyle("-fx-padding: 10px;");
        usernameColumn.setPrefWidth(150);
        table.getColumns().add(usernameColumn);

        // Populate table with employee data
        ObservableList<Employee> tableData = FXCollections.observableArrayList();
        tableData.addAll(employeeList);
        table.setItems(tableData);

        // Create back button
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            // Return to admin page when back button is clicked
            Admin.showAdminPage(primaryStage, user);
        });

        // Create border pane to hold table and button
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(table);
        borderPane.setBottom(backButton);
        BorderPane.setAlignment(backButton, Pos.CENTER);
        BorderPane.setMargin(backButton, new Insets(20));

        double preferredHeight = 300;
        borderPane.setPrefHeight(preferredHeight);

        // Create scene and set it to the primary stage
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
