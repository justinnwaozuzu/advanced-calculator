package calc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI extends JFrame {
    private AdvancedCalculator calculator;
    private ExpressionParser parser;
    private JTextField display;
    private JTextArea historyArea;
    private boolean newInput;
    
    public CalculatorGUI() {
        calculator = new AdvancedCalculator();
        parser = new ExpressionParser(calculator);
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("Advanced Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Display
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        
        // History area
        historyArea = new JTextArea(8, 20);
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane historyScroll = new JScrollPane(historyArea);
        
        // Create panels
        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        displayPanel.add(display, BorderLayout.CENTER);
        
        JPanel buttonPanel = createButtonPanel();
        
        // Main layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(displayPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(historyScroll, BorderLayout.SOUTH);
        
        add(mainPanel);
        pack();
        setSize(400, 600);
        setLocationRelativeTo(null);
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 5, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] buttons = {
            "C", "CE", "⌫", "÷", "√",
            "7", "8", "9", "×", "x²",
            "4", "5", "6", "-", "x^y",
            "1", "2", "3", "+", "log",
            "0", ".", "±", "=", "ln",
            "sin", "cos", "tan", "(", ")"
        };
        
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }
        
        return panel;
    }
    
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            
            try {
                switch (command) {
                    case "0": case "1": case "2": case "3": case "4":
                    case "5": case "6": case "7": case "8": case "9":
                        handleNumber(command);
                        break;
                    case ".":
                        handleDecimal();
                        break;
                    case "+": case "-": case "×": case "÷":
                        handleOperator(command);
                        break;
                    case "=":
                        handleEquals();
                        break;
                    case "C":
                        handleClear();
                        break;
                    case "CE":
                        handleClearEntry();
                        break;
                    case "⌫":
                        handleBackspace();
                        break;
                    case "√": case "x²": case "x^y": case "log": case "ln":
                    case "sin": case "cos": case "tan":
                        handleFunction(command);
                        break;
                    case "(": case ")":
                        handleParenthesis(command);
                        break;
                    case "±":
                        handleSignChange();
                        break;
                }
                updateHistoryDisplay();
            } catch (Exception ex) {
                display.setText("Error: " + ex.getMessage());
                newInput = true;
            }
        }
        
        private void handleNumber(String number) {
            if (newInput || display.getText().equals("0")) {
                display.setText(number);
                newInput = false;
            } else {
                display.setText(display.getText() + number);
            }
        }
        
        private void handleOperator(String op) {
            String current = display.getText();
            String displayOp = op.equals("×") ? "*" : op.equals("÷") ? "/" : op;
            display.setText(current + " " + displayOp + " ");
            newInput = false;
        }
        
        private void handleEquals() {
            String expression = display.getText()
                .replace("×", "*")
                .replace("÷", "/");
            double result = parser.evaluate(expression);
            display.setText(String.valueOf(result));
            newInput = true;
        }
        
        private void handleFunction(String func) {
            String current = display.getText();
            switch (func) {
                case "√": display.setText("sqrt(" + current + ")"); break;
                case "x²": display.setText(current + "^2"); break;
                case "x^y": display.setText(current + "^"); break;
                case "log": display.setText("log(" + current + ")"); break;
                case "ln": display.setText("ln(" + current + ")"); break;
                case "sin": display.setText("sin(" + current + ")"); break;
                case "cos": display.setText("cos(" + current + ")"); break;
                case "tan": display.setText("tan(" + current + ")"); break;
            }
            newInput = true;
        }
        
        private void handleClear() {
            display.setText("0");
            newInput = true;
        }
        
        private void handleClearEntry() {
            calculator.clearHistory();
            updateHistoryDisplay();
        }
        
        private void handleBackspace() {
            String current = display.getText();
            if (current.length() > 1) {
                display.setText(current.substring(0, current.length() - 1));
            } else {
                display.setText("0");
                newInput = true;
            }
        }
        
        private void handleParenthesis(String paren) {
            String current = display.getText();
            if (current.equals("0")) {
                display.setText(paren);
            } else {
                display.setText(current + paren);
            }
            newInput = false;
        }
        
        private void handleSignChange() {
            String current = display.getText();
            if (!current.equals("0")) {
                if (current.startsWith("-")) {
                    display.setText(current.substring(1));
                } else {
                    display.setText("-" + current);
                }
            }
        }
        
        private void handleDecimal() {
            String current = display.getText();
            if (!current.contains(".")) {
                display.setText(current + ".");
                newInput = false;
            }
        }
    }
    
    private void updateHistoryDisplay() {
        java.util.List<String> history = calculator.getHistory();
        StringBuilder historyText = new StringBuilder("Calculation History:\n");
        for (String entry : history) {
            historyText.append("• ").append(entry).append("\n");
        }
        historyArea.setText(historyText.toString());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculatorGUI().setVisible(true);
        });
    }
}