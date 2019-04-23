package com.nathanferguson.diceroller.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;

import java.io.IOException;

public class MenuView {
    
    @FXML
    public HBox menuBox;
    @FXML
    public VBox menuList;
    
    public void initialize() {
        try {
            FXMLLoader rollerViewLoader = new FXMLLoader(getClass().getResource("/FXML/RollerView.fxml"));
            rollerViewLoader.load();
            
            RollerViewController rollerViewController = rollerViewLoader.getController();
            Pane contentPane = rollerViewController.getBackground();
            HBox.setHgrow(contentPane, Priority.ALWAYS);
            menuBox.getChildren().add(contentPane);
            
        } catch(IOException e) {
            System.out.print("Exception thrown trying to load main screen: ");
            System.out.println(e.getMessage());
        }
    }
    
    @FXML
    public void toggleMenuVisibility(ActionEvent actionEvent) {
        menuList.setVisible(!menuList.isVisible());
        
        if(menuList.isVisible()) {
            menuList.setMinWidth(Region.USE_PREF_SIZE);
            menuList.setPrefWidth(100.0);
        } else {
            menuList.setMinWidth(0.0);
            menuList.setPrefWidth(0.0);
        }
    }
}
