package com.nathanferguson.diceroller.Controllers;


import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


public class RollerViewController {
    @FXML
    private TextField addDXField;
    @FXML
    private javafx.scene.control.Button rollAllButton;
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
    public void initialize() {
        dieGroups = new ArrayList<>();
        addDieGroup();
        
        runningTotalProperty = new SimpleIntegerProperty(0);
        runningTotalLabel.textProperty().bind(runningTotalProperty.asString());
    
        addDXField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { addDXField.setText(newValue.replaceAll("[^\\d]", "")); }
        });
        
        //masterPane.setPrefWidth(Double.MAX_VALUE);
    
        Platform.runLater(() -> rollAllButton.requestFocus());
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
        String inputString = addDXField.getText();
        if (inputString.length() < 1) {
            return;
        }
        int numSides = Integer.parseInt(inputString);
        if(numSides < 1) {
            JFrame frame = new JFrame("You think you're real clever");
            java.awt.Label label = new java.awt.Label("Draw a die with no sides then, genius.");
            label.setAlignment(java.awt.Label.CENTER);
            frame.getContentPane().add(label, BorderLayout.CENTER);
            frame.pack();
            frame.setLocation(400, 200);
            frame.setSize(800, 500);
            frame.setVisible(true);
            return;
        }
        addDie(numSides);
    }
    
    public void addToRunningTotal(int n) {
        runningTotalProperty.set(runningTotalProperty.get() + n);
    }
    
    public void resetRunningTotal(ActionEvent actionEvent) {
        runningTotalProperty.set(0);
    }
    
    public Pane getBackground() {
        return masterPane;
    }
    
}
