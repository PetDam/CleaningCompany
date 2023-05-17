package com.mycompany.cleaningcompany;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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

        ArrayList<Employee> employeeList = SystemIOHandler.readEmployeeFromFile(filePath);

        int numRows = employeeList.size();
        int numCols = 5;
        String[][] data = new String[numRows][numCols];

        // Add column headers
        String[] columnHeaders = { "Name", "Assign Work", "Hour", "Location", "Address" };

        // Add employee data
        for (int i = 0; i < numRows; i++) {
            Employee employee = employeeList.get(i);
            data[i] = new String[] {
                    employee.getUsername(),
                    employee.getAssignWork(),
                    employee.getHour(),
                    employee.getLocation(),
                    employee.getAddress()
            };
        }

        TableView<String[]> table = new TableView<>();

        // Create table columns
        for (int i = 0; i < numCols; i++) {
            TableColumn<String[], String> column = new TableColumn<>(columnHeaders[i]);
            final int colIndex = i;
            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[colIndex]));
            table.getColumns().add(column);
        }

        // Add table data
        ObservableList<String[]> tableData = FXCollections.observableArrayList();
        tableData.addAll(data);
        table.setItems(tableData);

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            LoginForm loginForm = new LoginForm();
            loginForm.start(new Stage());
            primaryStage.close();
        });
        
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(table);
        borderPane.setBottom(backButton);
        BorderPane.setAlignment(backButton, Pos.CENTER);
        BorderPane.setMargin(backButton, new Insets(20));
        
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}        
