package com.mycompany.cleaningcompany;

import java.util.ArrayList;
import java.lang.System;

public class EmployeeList {
    private static String filePath = "data/save/clients.dat";

    public static void main(String[] args) {
        ArrayList<Employee> employeeList = new ArrayList<Employee>();
        SystemIOHandler.addNewEmployeeManually(employeeList);
        addEmployees();
    }

    public static void addEmployees() {
        ArrayList<Employee> employeeList = SystemIOHandler.readEmployeeFromFile(filePath);

        
            for (Employee el : employeeList) {
                System.out.println("Employee " + el.getEmployeeID());
                System.out.println("- Name: " + el.getUsername());
                System.out.println("- Password: " + el.getPassword());
                System.out.println("- Assignment Work: " + el.getAssignWork());
                System.out.println("- Hour: " + el.getHour());
                System.out.println("- Location: " + el.getLocation());
                System.out.println("- Address: " + el.getAddress());
                System.out.println("------------------");
                

            }

            SystemIOHandler.storeEmployeeInFile(employeeList, filePath);

        
    }
}