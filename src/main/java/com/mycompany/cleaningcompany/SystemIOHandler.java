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
                    System.out.println("ERROR: Unable to unpack User object!");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Cannot find user file!");
        } catch (IOException e) {
            System.out.println("ERROR: There was a problem reading from file!");
        }

        return myEmployees;
    }

    public static void storeEmployeeInFile(ArrayList<Employee> employeeList, String filePath) {
        try (
                FileOutputStream employeeFile = new FileOutputStream(filePath);
                ObjectOutputStream employeeStream = new ObjectOutputStream(employeeFile)) {
            for (Employee employee : employeeList) {
                employeeStream.writeObject(employee);
            }
        } catch (IOException e) {
            System.out.println("ERROR: There was a problem writing to file!");
        }
    }
}
