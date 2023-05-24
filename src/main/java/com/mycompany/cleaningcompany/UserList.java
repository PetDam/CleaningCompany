package com.mycompany.cleaningcompany;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class UserList {

    public static ArrayList<User> readUserFromFile(String filePath) {
        ArrayList<User> myUsers = new ArrayList<User>();

        try (
                FileInputStream userFile = new FileInputStream(filePath);
                ObjectInputStream userStream = new ObjectInputStream(userFile);) {
            boolean endOfFile = false;
            while (!endOfFile) {
                try {
                    User newUser = (User) userStream.readObject();
                    myUsers.add(newUser);
                } catch (IOException e) {
                    endOfFile = true;
                } catch (ClassNotFoundException ex) {
                    System.out.println("ERROR: Unable to unpack User object!");
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR: There was a problem reading from file!");
        }

        return myUsers;
    }

    public static void storeUserInFile(ArrayList<User> userList, String filePath) {
        try (
                FileOutputStream userFile = new FileOutputStream(filePath);
                ObjectOutputStream userStream = new ObjectOutputStream(userFile);) {
            for (User user : userList) {
                userStream.writeObject(user);
            }
        } catch (IOException e) {
            System.out.println("ERROR: There was a problem writing to the file!");
        }
    }

    public static void addNewUser(String filePath, String username, String password) {
        ArrayList<User> userList = readUserFromFile(filePath);
        User newUser = new User( username, password);
        userList.add(newUser);
        storeUserInFile(userList, filePath);
    }
}

