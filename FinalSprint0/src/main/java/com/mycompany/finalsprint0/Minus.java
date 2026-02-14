package com.mycompany.finalsprint0;

/**
 * Minus is an intentionally flawed program that takes in 2 numbers and
 * subtracts them. The second number is forced to be positive so results are
 * unexpected.
 * @author David Boatright
 */
public class Minus {
    //The left and right hand numbers in the subtraction
    private int left;
    private int right;
    //Constructor
    public Minus(int left, int right){
        this.left = left;
        this.right = Math.abs(right);
    }
    //Return the result of the subtraction
    public int getValue(){
        return left - right;
    }
}
