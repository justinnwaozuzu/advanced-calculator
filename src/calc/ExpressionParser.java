package calc;

import java.util.*;

public class ExpressionParser {
    private AdvancedCalculator calculator;
    
    // Operator precedence (higher number = higher precedence)
    private static final Map<String, Integer> PRECEDENCE = new HashMap<>();
    static {
        PRECEDENCE.put("+", 1);
        PRECEDENCE.put("-", 1);
        PRECEDENCE.put("*", 2);
        PRECEDENCE.put("/", 2);
        PRECEDENCE.put("%", 2);
        PRECEDENCE.put("^", 3);
    }
    
    public ExpressionParser(AdvancedCalculator calculator) {
        this.calculator = calculator;
    }
    
    public double evaluate(String expression) {
        expression = expression.replaceAll("\\s+", ""); // Remove spaces
        List<String> tokens = tokenize(expression);
        return evaluateTokens(tokens);
    }
    
    private List<String> tokenize(String expr) {
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            
            if (Character.isDigit(c) || c == '.') {
                current.append(c);
            } else {
                // If we have a number, add it first
                if (current.length() > 0) {
                    tokens.add(current.toString());
                    current = new StringBuilder();
                }
                
                // Handle functions (sin, cos, sqrt, etc.)
                if (Character.isLetter(c)) {
                    while (i < expr.length() && Character.isLetter(expr.charAt(i))) {
                        current.append(expr.charAt(i));
                        i++;
                    }
                    i--; // Adjust for loop increment
                    tokens.add(current.toString());
                    current = new StringBuilder();
                } else {
                    // Handle operators and parentheses
                    tokens.add(String.valueOf(c));
                }
            }
        }
        
        // Add the last token if any
        if (current.length() > 0) {
            tokens.add(current.toString());
        }
        
        return tokens;
    }
    
    private double evaluateTokens(List<String> tokens) {
        // Convert to Reverse Polish Notation (RPN) using Shunting Yard algorithm
        List<String> rpn = shuntingYard(tokens);
        return evaluateRPN(rpn);
    }
    
    private List<String> shuntingYard(List<String> tokens) {
        List<String> output = new ArrayList<>();
        Stack<String> operators = new Stack<>();
        
        for (String token : tokens) {
            if (isNumber(token)) {
                output.add(token);
            } else if (isFunction(token)) {
                operators.push(token);
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                operators.pop(); // Remove the "("
                if (!operators.isEmpty() && isFunction(operators.peek())) {
                    output.add(operators.pop());
                }
            } else if (isOperator(token)) {
                while (!operators.isEmpty() && 
                       isOperator(operators.peek()) && 
                       hasHigherPrecedence(operators.peek(), token)) {
                    output.add(operators.pop());
                }
                operators.push(token);
            }
        }
        
        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }
        
        return output;
    }
    
    private double evaluateRPN(List<String> rpn) {
        Stack<Double> stack = new Stack<>();
        
        for (String token : rpn) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isFunction(token)) {
                double operand = stack.pop();
                double result = evaluateFunction(token, operand);
                stack.push(result);
            } else if (isOperator(token)) {
                double right = stack.pop();
                double left = stack.pop();
                double result = evaluateOperation(token, left, right);
                stack.push(result);
            }
        }
        
        return stack.pop();
    }
    
    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private boolean isOperator(String token) {
        return PRECEDENCE.containsKey(token);
    }
    
    private boolean isFunction(String token) {
        return token.matches("sqrt|sin|cos|tan|log|ln|arcsin|arccos|arctan|sinh|cosh|tanh");
    }
    
    private boolean hasHigherPrecedence(String op1, String op2) {
        return PRECEDENCE.get(op1) >= PRECEDENCE.get(op2);
    }
    
    private double evaluateFunction(String function, double operand) {
        switch (function) {
            case "sqrt": return calculator.squareRoot(operand);
            case "sin": return calculator.sin(operand);
            case "cos": return calculator.cos(operand);
            case "tan": return calculator.tan(operand);
            case "log": return calculator.logarithm(operand);
            case "ln": return calculator.naturalLogarithm(operand);
            case "arcsin": return calculator.arcsin(operand);
            case "arccos": return calculator.arccos(operand);
            case "arctan": return calculator.arctan(operand);
            case "sinh": return calculator.sinh(operand);
            case "cosh": return calculator.cosh(operand);
            case "tanh": return calculator.tanh(operand);
            default: throw new IllegalArgumentException("Unknown function: " + function);
        }
    }
    
    private double evaluateOperation(String operator, double left, double right) {
        switch (operator) {
            case "+": return calculator.add(left, right);
            case "-": return calculator.subtract(left, right);
            case "*": return calculator.multiply(left, right);
            case "/": return calculator.divide(left, right);
            case "%": return calculator.modulus(left, right);
            case "^": return calculator.power(left, right);
            default: throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}