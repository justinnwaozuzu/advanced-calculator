package calc;

import java.util.List;
import java.util.Scanner;

/**
 * This interface tests the calculators engine
 */
public class Main {
    private AdvancedCalculator calculator;
    private ExpressionParser parser;
    private Scanner scanner;
    
    public Main() {
        this.calculator = new AdvancedCalculator();
        this.parser = new ExpressionParser(calculator);
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("=== Advanced Calculator ===");
        System.out.println("Basic operations: +, -, *, /, %");
        System.out.println("Type 'help' for commands, 'exit' to quit");
        
        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("exit")) {
                break;
            } else if (input.equalsIgnoreCase("help")) {
                showHelp();
            } else if (input.equalsIgnoreCase("history")) {
                showHistory();
            } else if (input.equalsIgnoreCase("clear")) {
                calculator.clearHistory();
                System.out.println("History cleared");
            } else {
                processCalculation(input);
            }
        }
        
        System.out.println("Thank you for using Advanced Calculator!");
    }
    
    private void showHelp() {
    	System.out.println("\nAvailable Commands:");
        System.out.println("Basic Operations:");
        System.out.println("  a + b       - Addition");
        System.out.println("  a - b       - Subtraction");
        System.out.println("  a * b       - Multiplication");
        System.out.println("  a / b       - Division");
        System.out.println("  a % b       - Modulus");
        System.out.println("  a ^ b       - Power");
        System.out.println("  a root b    - Nth root");
        
        System.out.println("\nScientific Operations:");
        System.out.println("  sqrt n      - Square root");
        System.out.println("  log n       - Logarithm base 10");
        System.out.println("  ln n        - Natural logarithm");
        System.out.println("  n!          - Factorial");
        System.out.println("  sin n       - Sine (degrees)");
        System.out.println("  cos n       - Cosine (degrees)");
        System.out.println("  tan n       - Tangent (degrees)");
        System.out.println("  arcsin n    - Inverse sine");
        System.out.println("  arccos n    - Inverse cosine");
        System.out.println("  arctan n    - Inverse tangent");
        
        System.out.println("\nUtility Commands:");
        System.out.println("  history     - Show calculation history");
        System.out.println("  clear       - Clear history");
        System.out.println("  help        - Show this help");
        System.out.println("  exit        - Exit calculator");
    }
    
    private void showHistory() {
        System.out.println("\nCalculation History:");
        List<String> history = calculator.getHistory();
        if (history.isEmpty()) {
            System.out.println("No calculations yet");
        } else {
            for (String entry : history) {
                System.out.println("  " + entry);
            }
        }
    }
    
    private void processCalculation(String input) {
    	if (input.contains("+") || input.contains("-") || input.contains("*") || 
        	    input.contains("/") || input.contains("^") || input.contains("(")) {
        	    handleExpression(input);
        	    return;
        }
        try {
        	
        	input = input.trim();
            
            // Handle factorial operation (special case)
            if (input.endsWith("!")) {
                handleFactorial(input);
                return;
            }
            
            String[] tokens = input.split("\\s+");
            
            // Handle single operand operations
            if (tokens.length == 2) {
                double a = Double.parseDouble(tokens[1]);
                double result = performSingleOperation(tokens[0], a);
                System.out.println("Result: " + result);
                return;
            }
            
            if (tokens.length != 3) {
                System.out.println("Invalid format. Use: number operator number");
                return;
            }
            
            double a = Double.parseDouble(tokens[0]);
            String operator = tokens[1];
            double b = Double.parseDouble(tokens[2]);
            
            double result = performOperation(a, operator, b);
            System.out.println("Result: " + result);
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format");
        } catch (ArithmeticException e) {
            System.out.println("Math Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        
    }
    
    private void handleExpression(String expression) {
        try {
            double result = parser.evaluate(expression);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Expression Error: " + e.getMessage());
            System.out.println("Usage examples:");
            System.out.println("  (2 + 3) * 4");
            System.out.println("  sin(30) + cos(60)");
            System.out.println("  2^3 * sqrt(16)");
        }
    }
    
    private void handleFactorial(String input) {
        try {
            // Remove the ! and trim
            String numberStr = input.substring(0, input.length() - 1).trim();
            int number = Integer.parseInt(numberStr);
            
            double result = calculator.factorial(number);
            System.out.println("Result: " + result);
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number for factorial");
        } catch (ArithmeticException e) {
            System.out.println("Math Error: " + e.getMessage());
        }
    }
    
    private double performSingleOperation(String operator, double a) {
        switch (operator.toLowerCase()) {
            case "sqrt":
                return calculator.squareRoot(a);
            case "log":
                return calculator.logarithm(a);
            case "ln":
                return calculator.naturalLogarithm(a);
            case "!":
                return calculator.factorial((int) a);
            case "sin":
            	return calculator.sin(a);
            case "cos":
            	return calculator.cos(a);
            case "tan":
            	return calculator.tan(a);
            case "arcsin":
            	return calculator.arcsin(a);
            case "arccos":
            	return calculator.arccos(a);
            case "arctan":
            	return calculator.arctan(a);
            case "sinh":
            	return calculator.sinh(a);
            case "cosh":
            	return calculator.cosh(a);
            case "tanh":
            	return calculator.tanh(a);
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
    
    private double performOperation(double a, String operator, double b) {
        switch (operator) {
            case "+":
                return calculator.add(a, b);
            case "-":
                return calculator.subtract(a, b);
            case "*":
                return calculator.multiply(a, b);
            case "/":
                return calculator.divide(a, b);
            case "%":
                return calculator.modulus(a, b);
            case "^":
            	return calculator.power(a, b);
            case "root":
            	return calculator.nthRoot(a, b);
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
    
    public static void main(String[] args) {
        new Main().start();
    }
}