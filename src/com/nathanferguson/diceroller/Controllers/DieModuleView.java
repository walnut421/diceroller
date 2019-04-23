package com.nathanferguson.diceroller.Controllers;

import com.nathanferguson.diceroller.Dice.MultiDie;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class DieModuleView {
    
    
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
    private TextField diceNumField;
    
    @FXML
    private Label dieTypeLabel;
    
    @FXML
    private Label modifierLabel;
    private SimpleStringProperty modifierText;
    @FXML
    private TextField modifierField;
    
    
    private DieGroupView dieGroup;
    
    private MultiDie multiDie;
    
    public void init(int dieSides, DieGroupView group) throws IllegalArgumentException {
        
        multiDie = new MultiDie(dieSides);
        multiDie.addDie();
    
        this.dieGroup = group;
        
        dieTypeLabel.setText("d" + Integer.toString(dieSides));
        
        rollTotalText = new SimpleStringProperty("");
        rollTotalLabel.textProperty().bind(rollTotalText);
        
        rollText = new SimpleStringProperty("");
        rollLabel.textProperty().bind(rollText);
        
        //link diceNum variables
        diceNumText = new SimpleStringProperty("");
        diceNumLabel.textProperty().bind(diceNumText);
        diceNumLabel.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                setEditing(diceNumLabel, true);
            }
        });
        diceNumField.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                setEditing(diceNumLabel, false);
            }
        });
        diceNumField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if(!newVal) {
                setEditing(diceNumLabel, false);
            }
        });
        diceNumField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { diceNumField.setText(newValue.replaceAll("[^\\d]", "")); }
        });
        
        //link modifier variables
        modifierText = new SimpleStringProperty("");
        modifierLabel.textProperty().bind(modifierText);
        modifierLabel.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                setEditing(modifierLabel, true);
            }
        });
        modifierField.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                setEditing(modifierLabel, false);
            }
        });
        modifierField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if(!newVal) {
                setEditing(modifierLabel, false);
            }
        });
        //Check for valid input
        modifierField.textProperty().addListener((observable, oldValue, newValue) -> {
            String sign;
            String numberValue;
            
            //Split string into number and sign
            if(newValue.startsWith("-") || newValue.startsWith("+")) {
                sign = newValue.substring(0,1);
                numberValue = newValue.substring(1);
            } else {
                sign = "";
                numberValue = newValue;
            }
            //Replace non-digits
            if (!numberValue.matches("\\d*")) {
                numberValue = numberValue.replaceAll("[^\\d]", "");
            }
            
            if(!newValue.equals(sign + numberValue)) {
                modifierField.setText(sign + numberValue);
            }
        });
    
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
    
    public void setNumberOfDice(int n) {
        multiDie.setNumberOfDice(n);
    }
    
    public void setModifier(int n) {
        multiDie.setModifier(n);
    }
    
    @Override
    public String toString() {
        String numDiceString = Integer.toString(multiDie.getNumberOfDice());
        String numSidesString = Integer.toString(multiDie.getNumberOfSides());
        String modifierString = Integer.toString(multiDie.getModifier());
        if(!modifierString.startsWith("-")) {
            modifierString = "+" + modifierString;
        }
        return numDiceString + "d" + numSidesString + modifierString;
    }
    
    private void setEditing(Label label, boolean isEditing) {
        if(label == diceNumLabel) {
            if(isEditing) {
                diceNumField.setText(diceNumLabel.getText());
                diceNumLabel.setVisible(false);
                diceNumField.setVisible(true);
                Platform.runLater(() -> diceNumField.requestFocus());
                
            } else {
                if(diceNumField.getText().length() > 0) {
                    multiDie.setNumberOfDice(Integer.parseInt(diceNumField.getText()));
                }
                updateDieElements();
                diceNumField.setVisible(false);
                diceNumLabel.setVisible(true);
            }
        } else if(label == modifierLabel) {
            if(isEditing) {
                modifierLabel.setVisible(false);
                modifierField.setText(modifierLabel.getText());
                modifierField.setVisible(true);
                Platform.runLater(() -> modifierField.requestFocus());
            } else {
                if(modifierField.getText().length() > 0 && !modifierField.getText().equals("-")) {
                    multiDie.setModifier(Integer.parseInt(modifierField.getText()));
                }
                updateDieElements();
                modifierField.setVisible(false);
                modifierLabel.setVisible(true);
            }
        }
    }
    
    private void updateDieElements() {
        //Update number of dice
        diceNumText.set(Integer.toString(multiDie.getNumberOfDice()));
        //Update modifier
        String modifierStringNew = Integer.toString(multiDie.getModifier());
        if(!modifierStringNew.startsWith("-")) {
            modifierStringNew = "+" + modifierStringNew;
        }
        modifierText.set(modifierStringNew);
    }
    
    private void updateRollElements() {
        //Update total
        rollTotalText.set(Integer.toString(multiDie.getRoll()));
        //Update individual roll values
        String rollValuesString = multiDie.getValues().toString();
        rollValuesString = rollValuesString.replace("[", "(");
        rollValuesString = rollValuesString.replace("]", ")");
        rollText.set(rollValuesString);
    }
}
