package com.nathanferguson.diceroller.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

public class DieGroupView {
    
    @FXML
    private CheckBox isAddedToRunningTotalCheckBox;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private FlowPane dicePane;
    @FXML
    private Label rollTotalLabel;
    private SimpleStringProperty rollTotalText;
    private ArrayList<DieModuleView> dice;
    private RollerViewController rollerViewController;
    
    
    public void init(RollerViewController rollerViewController) {
        this.rollerViewController = rollerViewController;
        dice = new ArrayList<>();
        
        rollTotalText = new SimpleStringProperty("");
        rollTotalLabel.textProperty().bind(rollTotalText);
        
        mainPane.setOnMousePressed(event -> rollerViewController.setActiveDieGroup(this));
    }
    
    public void roll() {
        for (DieModuleView die : dice) {
            die.roll();
        }
        if(isAddedToRunningTotal()) {
            rollerViewController.addToRunningTotal(getRoll());
        }
        updateRollElements();
    }
    
    public int getRoll() {
        int roll = 0;
        for(DieModuleView die : dice) {
            roll += die.getRoll();
        }
        return roll;
    }
    
    public DieModuleView addDie(int numSides) throws IllegalArgumentException, IOException {
        
        FXMLLoader dieModuleLoader = new FXMLLoader(getClass().getResource("/FXML/DieModule.fxml"));
        
        dieModuleLoader.load();
        
        DieModuleView dieModuleController = dieModuleLoader.getController();
        dieModuleController.init(numSides, this);
        dice.add(dieModuleController);
        dicePane.getChildren().addAll(dieModuleController.getMainPane());
        return dieModuleController;
    }
    
    public DieModuleView addDie(String s) throws IllegalArgumentException, IOException {
        //Sanitize input
        s = s.replaceAll("\\s", "").replaceAll("D", "d");
        if(!s.matches("\\d*d\\d+([-+]\\d+)?")) {
            throw new IllegalArgumentException("Input must match format XdN+/-M");
        }
        
        //Split string into separate values
        String numDiceString = s.replaceAll("d.*", "");
        String numSidesString = s.replaceAll("\\d*d", "").replaceAll("[-+]\\d*","");
        String modifierString = s.replaceAll("\\d*d\\d*","");
        
        //Parse values
        int numDice, numSides, modifier;
        if(numDiceString.length() == 0) {
            numDice = 0;
        } else {
            numDice = Integer.parseInt(numDiceString);
        }
        numSides = Integer.parseInt(numSidesString);
        if(modifierString.length() == 0) {
            modifier = 0;
        } else {
            modifier = Integer.parseInt(modifierString);
        }
    
        //Create MultiDie with given parameters
        
        FXMLLoader dieModuleLoader = new FXMLLoader(getClass().getResource("/FXML/DieModule.fxml"));
        
        dieModuleLoader.load();
        
        DieModuleView dieModuleController = dieModuleLoader.getController();
        dieModuleController.init(numSides, this);
        dieModuleController.setNumberOfDice(numDice);
        dieModuleController.setModifier(modifier);
        dice.add(dieModuleController);
        dicePane.getChildren().addAll(dieModuleController.getMainPane());
        return dieModuleController;
    }
    
    public void removeDie(DieModuleView die) {
        dicePane.getChildren().removeAll(die.getMainPane());
        dice.remove(die);
    }
    
    public Pane getMainPane() {
        return mainPane;
    }
    
    public boolean isAddedToRunningTotal() {
        return isAddedToRunningTotalCheckBox.isSelected();
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");
        for(DieModuleView die : dice) {
            s.append(die.toString());
            s.append(",");
        }
        //remove last comma and end string
        s.deleteCharAt(s.lastIndexOf(","));
        s.append("]");
        return s.toString();
    }
    
    private void updateRollElements() {
        rollTotalText.set(Integer.toString(getRoll()));
    }
    
    public void delete(ActionEvent actionEvent) {
        rollerViewController.removeDieGroup(this);
    }
}
