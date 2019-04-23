package com.nathanferguson.diceroller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //new File(System.getProperty("user.dir") + "/path").mkdirs();
        
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Menu.fxml"));
        primaryStage.setTitle("Dice Roller");
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
