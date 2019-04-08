package com.nathanferguson.diceroller.Controllers;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;


public class RollerViewController {
    @FXML
    private Label runningTotalLabel;
    private SimpleIntegerProperty runningTotalProperty;
    @FXML
    private HBox dieGroupBox;
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
        
        runningTotalProperty = new SimpleIntegerProperty(0);
        runningTotalLabel.textProperty().bind(runningTotalProperty.asString());
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
            dieGroupView.init(this);
            dieGroups.add(dieGroupView);
            ObservableList<Node> dieGroups = dieGroupBox.getChildren();
            dieGroups.add(dieGroups.size() - 1, dieGroupView.getMainPane());
            
            setActiveDieGroup(dieGroupView);
            
        } catch(IOException e) {
            System.out.print("Exception thrown while trying to add die group: ");
            System.out.println(e.getMessage());
        }
    }
    
    public void removeDieGroup(DieGroupView group) {
        dieGroupBox.getChildren().removeAll(group.getMainPane());
        dieGroups.remove(group);
    }
    
    public void setActiveDieGroup(DieGroupView newGroup) {
        if(activeDieGroup != null) {
            activeDieGroup.getMainPane().setStyle("-fx-background-color: #dddddd");
        }
        newGroup.getMainPane().setStyle("-fx-background-color: #c0d0ff");
        activeDieGroup = newGroup;
    }
    
    public void addDie(int numSides) {
        try {
            activeDieGroup.addDie(numSides);
        } catch (NullPointerException e) {}
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
    
    public void addToRunningTotal(int n) {
        runningTotalProperty.set(runningTotalProperty.get() + n);
    }
    
    public void resetRunningTotal(ActionEvent actionEvent) {
        runningTotalProperty.set(0);
    }
    
}
