package com.mycompany.cleaningcompany;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class AdminList {
    public static ArrayList<Admin> readAdminFromFile(String filePath) {
        ArrayList<Admin> myAdmins = new ArrayList<Admin>();

        try (
                FileInputStream adminFile = new FileInputStream(filePath);
                ObjectInputStream adminStream = new ObjectInputStream(adminFile);) {
            boolean endOfFile = false;
            while (!endOfFile) {
                try {
                    Admin newAdmin = (Admin) adminStream.readObject();
                    myAdmins.add(newAdmin);
                } catch (IOException e) {
                    endOfFile = true;
                } catch (ClassNotFoundException ex) {
                    System.out.println("ERROR: Unable to unpack Admin object!");
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR: There was a problem reading from file!");
        }

        return myAdmins;
    }

    public static void storeAdminInFile(ArrayList<Admin> adminList, String filePath) {
        try (
                FileOutputStream adminFile = new FileOutputStream(filePath);
                ObjectOutputStream adminStream = new ObjectOutputStream(adminFile);) {
            for (Admin admin : adminList) {
                adminStream.writeObject(admin);
            }
        } catch (IOException e) {
            System.out.println("ERROR: There was a problem writing to the file!");
        }
    }

    public static void addNewAdmin(String filePath, String username, String password) {
        ArrayList<Admin> adminList = readAdminFromFile(filePath);
        Admin newAdmin = new Admin(username, password);
        adminList.add(newAdmin);
        storeAdminInFile(adminList, filePath);
    }
}
