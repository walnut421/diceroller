package com.nathanferguson.diceroller.Controllers;

import com.nathanferguson.diceroller.Dice.Rollable;
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

public class DieGroupView implements Rollable {
    
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
    
    public void addDie(int numSides) {
        try {
            FXMLLoader dieModuleLoader = new FXMLLoader(getClass().getResource("/FXML/DieModule.fxml"));
            
            dieModuleLoader.load();
            
            DieModuleView dieModuleController = dieModuleLoader.getController();
            dieModuleController.init(numSides, this);
            dice.add(dieModuleController);
            dicePane.getChildren().addAll(dieModuleController.getMainPane());
            
        } catch(IOException e) {
            System.out.print("Exception thrown while trying to add die: ");
            System.out.println(e.getMessage());
        }
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
    
    private void updateRollElements() {
        rollTotalText.set(Integer.toString(getRoll()));
    }
    
    public void delete(ActionEvent actionEvent) {
        rollerViewController.removeDieGroup(this);
    }
}
