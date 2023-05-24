package com.mycompany.cleaningcompany;

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
import javafx.stage.Stage;

import java.util.ArrayList;

public class Employee extends User {
    private int employeeID;
    private static int currentEmployeeId = 1;
    private String assignWork;
    private String hour;
    private String location;
    private String address;
    private static final String filePath = "data/save/users.dat";

    public Employee(String username, String password, String assignWork, String hour, String location,
                    String address) {
        super(username, password);
        this.employeeID = getNextEmployeeId();
        this.assignWork = assignWork;
        this.hour = hour;
        this.location = location;
        this.address = address;
    }

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

    public static void showEmployeePage(Stage primaryStage, User user) {
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
