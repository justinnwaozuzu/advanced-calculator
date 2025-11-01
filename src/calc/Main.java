package calc;

import java.util.List;
import java.util.Scanner;

/**
 * This interface tests the calculators engine
 */
public class Main {
    private AdvancedCalculator calculator;
    private Scanner scanner;
    
    public Main() {
        this.calculator = new AdvancedCalculator();
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
        System.out.println("  a + b       - Addition");
        System.out.println("  a - b       - Subtraction");
        System.out.println("  a * b       - Multiplication");
        System.out.println("  a / b       - Division");
        System.out.println("  a % b       - Modulus");
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
        try {
            String[] tokens = input.split("\\s+");
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
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
    
    public static void main(String[] args) {
        new Main().start();
    }
}