package com.mycompany.cleaningcompany;

import java.util.ArrayList;

public class Employee extends User {
    public static ArrayList<Employee> employeeList = SystemIOHandler.readEmployeeFromFile("data/save/clients.dat");

    private int employeeID;
    private static int currentEmployeeId = 1;
    private String username;
    private String password;
    private String assignWork;
    private String hour;
    private String location;
    private String address;

    public Employee(String username, String password, String assignWork, String hour, String location,
            String address) {
        this.employeeID = getNextEmployeeId();
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
