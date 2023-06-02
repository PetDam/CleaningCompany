package com.mycompany.cleaningcompany;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        // Δημιουργία ενός αντικειμένου LoginForm
        LoginForm loginForm = new LoginForm();

        // Κλήση της μεθόδου start() του LoginForm
        loginForm.start(primaryStage);
    }
}
