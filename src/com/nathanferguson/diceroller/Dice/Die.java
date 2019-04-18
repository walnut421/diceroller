package com.nathanferguson.diceroller.Dice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class Die {
    private final int numberOfSides;
    private int rolledNumber;
    
    /**
     * Creates a new die with the number of sides specified. Once the die is created the number of sides cannot be changed.
     * @param numberOfSides int how many sides the new Die has. Must be greater than or equal to 1.
     */
    public Die(int numberOfSides) throws IllegalArgumentException {
        if(numberOfSides < 1) throw new IllegalArgumentException("The number of sides must be at least 1");
        this.numberOfSides = numberOfSides;
        this.rolledNumber = 1;
    }
    
    /**
     * Rolls the die, changing the number that has been rolled
     */
    public void roll() {
        rolledNumber = ThreadLocalRandom.current().nextInt(1, numberOfSides + 1);
    }
    
    /**
     * Resets the die roll to 1 (the minimum)
     */
    public void reset() {
        this.rolledNumber = 1;
    }
    
    /**
     * Gets the number that has been rolled on the die
     * @return int the number rolled
     */
    public int getRoll() {
        return rolledNumber;
    }
}
