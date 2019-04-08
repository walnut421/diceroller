package com.nathanferguson.diceroller.Controllers;

import com.nathanferguson.diceroller.Dice.MultiDie;
import com.nathanferguson.diceroller.Dice.Rollable;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class DieModuleView implements Rollable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label rollLabel;
    private SimpleStringProperty rollText;
    @FXML
    private Label rollTotalLabel;
    private SimpleStringProperty rollTotalText;
    @FXML
    private Label diceNumLabel;
    private SimpleStringProperty diceNumText;
    @FXML
    private Label dieTypeLabel;
    @FXML
    private Label modifierLabel;
    private SimpleStringProperty modifierText;
    
    
    private DieGroupView dieGroup;
    
    private MultiDie multiDie;
    
    public void init(int dieSides, DieGroupView group) {
        
        multiDie = new MultiDie(dieSides);
        multiDie.addDie();
    
        this.dieGroup = group;
        
        dieTypeLabel.setText("d" + Integer.toString(dieSides));
        
        rollTotalText = new SimpleStringProperty("");
        rollTotalLabel.textProperty().bind(rollTotalText);
        
        rollText = new SimpleStringProperty("");
        rollLabel.textProperty().bind(rollText);
        
        diceNumText = new SimpleStringProperty("");
        diceNumLabel.textProperty().bind(diceNumText);
        
        modifierText = new SimpleStringProperty("");
        modifierLabel.textProperty().bind(modifierText);
    
        updateDieElements();
    }
    
    public void roll() {
        multiDie.roll();
        updateRollElements();
    }
    
    public int getRoll() {
        return multiDie.getRoll();
    }
    
    public Pane getMainPane() {
        return mainPane;
    }
    
    @FXML
    public void delete(ActionEvent actionEvent) {
        dieGroup.removeDie(this);
    }
    
    @FXML
    public void incrementDiceNumber(ActionEvent actionEvent) {
        multiDie.addDie();
        updateDieElements();
    }
    
    @FXML
    public void decrementDiceNumber(ActionEvent actionEvent) {
        multiDie.removeDie();
        updateDieElements();
    }
    
    @FXML
    public void incrementModifier(ActionEvent actionEvent) {
        multiDie.setModifier(multiDie.getModifier() + 1);
        updateDieElements();
    }
    
    @FXML
    public void decrementModifier(ActionEvent actionEvent) {
        multiDie.setModifier(multiDie.getModifier() - 1);
        updateDieElements();
    }
    
    private void updateDieElements() {
        
        diceNumText.set(Integer.toString(multiDie.getNumberOfDice()));
        String modifierStringNew = Integer.toString(multiDie.getModifier());
        if(!modifierStringNew.startsWith("-")) {
            modifierStringNew = "+" + modifierStringNew;
        }
        modifierText.set(modifierStringNew);
    }
    
    private void updateRollElements() {
        rollTotalText.set(Integer.toString(multiDie.getRoll()));
        String rollValuesString = multiDie.getValues().toString();
        rollValuesString = rollValuesString.replace("[", "(");
        rollValuesString = rollValuesString.replace("]", ")");
        rollText.set(rollValuesString);
    }
}
