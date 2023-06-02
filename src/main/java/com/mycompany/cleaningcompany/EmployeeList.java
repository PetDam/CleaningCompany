package com.mycompany.cleaningcompany;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class EmployeeList {

    // Read employee objects from file
    public static ArrayList<Employee> readEmployeeFromFile(String filePath) {
        ArrayList<Employee> myEmployees = new ArrayList<Employee>();

        try (
                FileInputStream employeeFile = new FileInputStream(filePath);
                ObjectInputStream employeeStream = new ObjectInputStream(employeeFile);) {
            boolean endOfFile = false;
            while (!endOfFile) {
                try {
                    Employee newEmployee = (Employee) employeeStream.readObject();
                    myEmployees.add(newEmployee);
                } catch (IOException e) {
                    endOfFile = true;
                } catch (ClassNotFoundException ex) {
                    System.out.println("ERROR: Unable to unpack Employee object!");
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR: There was a problem reading from file!");
        }

        return myEmployees;
    }

    // Store employee objects in file
    public static void storeEmployeeInFile(ArrayList<Employee> employeeList, String filePath) {
        try (
                FileOutputStream employeeFile = new FileOutputStream(filePath);
                ObjectOutputStream employeeStream = new ObjectOutputStream(employeeFile);) {
            for (Employee employee : employeeList) {
                employeeStream.writeObject(employee);
            }
        } catch (IOException e) {
            System.out.println("ERROR: There was a problem writing to the file!");
        }
    }

    // Add a new employee to the file
    public static void addNewEmployee(String filePath, String username, String password) {
        ArrayList<Employee> employeeList = readEmployeeFromFile(filePath);
        Employee newEmployee = new Employee(username, password);
        employeeList.add(newEmployee);
        storeEmployeeInFile(employeeList, filePath);
    }
}
