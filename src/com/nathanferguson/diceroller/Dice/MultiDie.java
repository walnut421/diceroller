package com.nathanferguson.diceroller.Dice;

import java.util.ArrayList;
import java.util.Collection;

public class MultiDie implements Rollable, DieSet {
    private ArrayList<Die> dice;
    private final int numberOfSides;
    private int modifier;
    
    /**
     * MultiDie represents rolling multiple dice of the same value, with a modifier.
     * Once the MultiDie is created, the number of sides cannot change
     * @param numberOfSides int the number of sides of the die
     * @throws IllegalArgumentException
     */
    public MultiDie(int numberOfSides) throws IllegalArgumentException {
        if(numberOfSides < 1) throw new IllegalArgumentException("The number of sides must be at least 1");
        this.numberOfSides = numberOfSides;
        this.modifier = 0;
        this.dice = new ArrayList<>();
    }
    
    /**
     * Rolls all the dice in the group
     */
    public void roll() {
        for(Die die : dice) {
            die.roll();
        }
    }
    
    /**
     * Gives the total roll of the group
     * @return int total
     */
    public int getRoll() {
        int total = 0;
        for(Die die : dice) {
            total += die.getRoll();
        }
        total += modifier;
        return total;
    }
    
    /**
     * Gives the individual die rolls in the multidie, as an arraylist of integers
     * @return ArrayList<Die> the dice in the group
     */
    public Collection<Integer> getValues() {
        ArrayList<Integer> values = new ArrayList<>();
        for (Die die : dice) {
            values.add(die.getRoll());
        }
        return values;
    }
    
    /**
     * Gives the Die objects stored in the multidie
     * @return
     */
    public Collection<Die> getDice() {
        return (ArrayList<Die>) dice.clone();
    }
    
    /**
     * Sets the number of dice to be rolled
     * @param n int number of dice to roll
     */
    public void setNumberOfDice(int n) {
        if(n > dice.size()) {
            addDice(n - dice.size());
        } else {
            removeDice(dice.size() - n);
        }
    }
    
    public int getNumberOfDice() {
        return dice.size();
    }
    
    /**
     * Adds 1 die to be rolled
     */
    public void addDie() {
        addDice(1);
    }
    
    /**
     * Adds n dice
     * @param n number of dice to add
     */
    public void addDice(int n) {
        for(int i = 0; i < n; i++) {
            dice.add(new Die(numberOfSides));
        }
    }
    
    /**
     * Removes 1 die
     */
    public void removeDie() {
        removeDice(1);
    }
    
    /**
     * Removes n dice, down to 0.
     * @param n number of dice to remove
     */
    public void removeDice(int n) {
        for(int i = 0; i < n; i++) {
            if(dice.isEmpty()) break;
            dice.remove(dice.size() - 1);
        }
    }
    
    
    
    public int getNumberOfSides() {
        return numberOfSides;
    }
    
    
    
    /**
     * Gets the roll modifier
     * @return int modifier
     */
    public int getModifier() {
        return modifier;
    }
    
    /**
     * Sets the roll modifier
     * @param modifier int new modifier
     */
    public void setModifier(int modifier) {
        this.modifier = modifier;
    }
}
