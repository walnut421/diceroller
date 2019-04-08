package com.nathanferguson.diceroller.Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;


public class RollerViewController {
    public HBox dieGroupBox;
    private ArrayList<DieGroupView> dieGroups;
    private DieGroupView activeDieGroup;
    
    @FXML
    private AnchorPane masterPane;
    @FXML
    private HBox diceBag;
    
    @FXML
    public void initialize() {
        dieGroups = new ArrayList<>();
        addDieGroup();
        
        //masterPaneInner.prefWidthProperty().bind(masterPane.prefViewportWidthProperty());
        //masterPaneInner.prefHeightProperty().bind(masterPane.prefViewportHeightProperty());
    }
    
    public void roll(ActionEvent actionEvent) {
        for (DieGroupView dieGroup : dieGroups) {
            dieGroup.roll();
        }
    }
    
    public int getRoll() {
        int roll = 0;
        for (DieGroupView dieGroup : dieGroups) {
            roll += dieGroup.getRoll();
        }
        return roll;
    }
    
    public void addDieGroup() {
        try{
            FXMLLoader dieGroupLoader = new FXMLLoader(getClass().getResource("/FXML/DieGroup.fxml"));
            dieGroupLoader.load();
            
            DieGroupView dieGroupView = dieGroupLoader.getController();
            dieGroups.add(dieGroupView);
            dieGroupBox.getChildren().addAll(dieGroupView.getMainPane());
            
            activeDieGroup = dieGroupView;
            
        } catch(IOException e) {
            System.out.print("Exception thrown while trying to add die group: ");
            System.out.println(e.getMessage());
        }
    }
    
    public void addDie(int numSides) {
        activeDieGroup.addDie(numSides);
    }
    
    public void addD4(ActionEvent actionEvent) {
        addDie(4);
    }
    
    public void addD6(ActionEvent actionEvent) {
        addDie(6);
    }
    
    public void addD8(ActionEvent actionEvent) {
        addDie(8);
    }
    
    public void addD10(ActionEvent actionEvent) {
        addDie(10);
    }
    
    public void addD12(ActionEvent actionEvent) {
        addDie(12);
    }
    
    public void addD20(ActionEvent actionEvent) {
        addDie(20);
    }
    
    public void addD100(ActionEvent actionEvent) {
        addDie(100);
    }
    
    public void addDX(ActionEvent actionEvent) {
    }
    
    
    
    
}
