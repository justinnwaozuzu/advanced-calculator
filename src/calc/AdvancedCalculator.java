package calc;

import java.math.MathContext;
import java.util.*;

/**
 * Advanced Calculator Core Engine
 * Handles mathematical operations and maintains calculation history
 */
public class AdvancedCalculator {
    private double memory;
    private List<String> history;
    private MathContext mathContext;
    
    public AdvancedCalculator() {
        this.memory = 0;
        this.history = new ArrayList<>();
        this.mathContext = new MathContext(10); // 10 decimal precision
    }
    
    // Basic arithmetic operations
    public double add(double a, double b) {
        double result = a + b;
        addToHistory(a + " + " + b + " = " + result);
        return result;
    }
    
    public double subtract(double a, double b) {
        double result = a - b;
        addToHistory(a + " - " + b + " = " + result);
        return result;
    }
    
    public double multiply(double a, double b) {
        double result = a * b;
        addToHistory(a + " * " + b + " = " + result);
        return result;
    }
    
    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        double result = a / b;
        addToHistory(a + " / " + b + " = " + result);
        return result;
    }
    
    public double modulus(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot calculate modulus with zero divisor");
        }
        double result = a % b;
        addToHistory(a + " % " + b + " = " + result);
        return result;
    }
    
    private void addToHistory(String operation) {
        history.add(operation);
        // Keep only last 50 operations
        if (history.size() > 50) {
            history.remove(0);
        }
    }
    
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }
    
    public void clearHistory() {
        history.clear();
    }
    
    // Memory operations
    public void memoryStore(double value) {
        this.memory = value;
        addToHistory("Memory stored: " + value);
    }
    
    public double memoryRecall() {
        return memory;
    }
    
    public void memoryClear() {
        this.memory = 0;
        addToHistory("Memory cleared");
    }
    
    public double memoryAdd(double value) {
        this.memory += value;
        addToHistory("Memory added: " + value + ", current: " + memory);
        return memory;
    }
    
    public double memorySubtract(double value) {
        this.memory -= value;
        addToHistory("Memory subtracted: " + value + ", current: " + memory);
        return memory;
    }
}