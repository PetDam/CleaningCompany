package com.mycompany.cleaningcompany;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SystemIOHandler {

    // Method to read input from the console
    public static String readInputFromConsole() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = null;
        try {
            input = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    // Method to read employee data from a file
    public static ArrayList<Employee> readEmployeeFromFile(String filePath) {
        ArrayList<Employee> myEmployees = new ArrayList<>();

        try (
                FileInputStream employeeFile = new FileInputStream(filePath);
                ObjectInputStream employeeStream = new ObjectInputStream(employeeFile)) {
            boolean endOfFile = false;
            while (!endOfFile) {
                try {
                    // Read an Employee object from the file
                    Employee newEmployee = (Employee) employeeStream.readObject();
                    myEmployees.add(newEmployee);
                } catch (IOException e) {
                    endOfFile = true;
                } catch (ClassNotFoundException ex) {
                    System.out.println("ERROR: Unable to unpack Employee object!");
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR: There was a problem reading from the file!");
        }

        return myEmployees;
    }

    // Method to store employee data in a file
    public static void storeEmployeeInFile(ArrayList<Employee> employeeList, String filePath) {
        try (
                FileOutputStream employeeFile = new FileOutputStream(filePath);
                ObjectOutputStream employeeStream = new ObjectOutputStream(employeeFile)) {
            for (Employee employee : employeeList) {
                // Write each Employee object to the file
                employeeStream.writeObject(employee);
            }
        } catch (IOException e) {
            System.out.println("ERROR: There was a problem writing to the file!");
        }
    }
}
