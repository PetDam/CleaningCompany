package com.mycompany.cleaningcompany;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class SystemIOHandler {

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
                } catch (EOFException e) {
                    endOfFile = true;
                } catch (ClassNotFoundException ex) {
                    System.out.println("ERROR: Unable to unpack ClientV2 object!");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Cannot find client file!");
        } catch (IOException e) {
            System.out.println("ERROR: There was a problem reading from file!");
        }

        return myEmployees;
    }

    public static void storeEmployeeInFile(ArrayList employeeList, String filePath) {
        try (
                FileOutputStream employeeFile = new FileOutputStream(filePath);
                ObjectOutputStream employeeStream = new ObjectOutputStream(employeeFile);) {
            for (Object el : employeeList) {
                employeeStream.writeObject(el);
            }
        } catch (IOException e) {
            System.out.println("ERROR: There was a problem writing to file!");
        }
    }

    public static void addNewEmployeeManually(ArrayList<Employee> employeeList) {
        String employeeFile = "data/save/clients.dat";
        try (Scanner input = new Scanner(java.lang.System.in)) {
            String addEmployee = "";
            String addPassword = "";
            String addAssignWork = "";
            String addHour = "";
            String addLocation = "";
            String addAddress = "";
            boolean isValidInput = false;

            while (!isValidInput) {
                try {
                    System.out.println("Enter employee's name:");
                    addEmployee = input.nextLine();
                    System.out.println("Enter employee's password:");
                    addPassword = input.nextLine();
                    System.out.println("Enter employee's assign work:");
                    addAssignWork = input.nextLine();
                    System.out.println("Enter the time for the employee's work:");
                    addHour = input.nextLine();
                    System.out.println("Enter the location for the employee's job:");
                    addLocation = input.nextLine();
                    System.out.println("Enter the address for the employee's job:");
                    addAddress = input.nextLine();

                    // Validate input and perform necessary checks

                    isValidInput = true;
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter valid values for all attributes.");
                    input.nextLine(); // Consume remaining input
                }
            }

            Employee newEmployee = new Employee(addEmployee, addPassword, addAssignWork, addHour, addLocation, addAddress);
            employeeList.add(newEmployee);

            SystemIOHandler.storeEmployeeInFile(employeeList, employeeFile);

        }
    }
}
