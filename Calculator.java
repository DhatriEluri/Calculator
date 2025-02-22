package calculator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private String expression = "";

    private final String[] buttonLabels = {
            "AC", "%", "C", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "00", "0", ".", "="
    };

    private final JButton[] buttons = new JButton[buttonLabels.length];

    public Calculator() {
        setTitle("Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display setup
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 36));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(display, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBackground(Color.BLACK);

        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 24));
            buttons[i].setFocusPainted(false);
            buttons[i].setOpaque(true);
            buttons[i].setBorderPainted(false);

            if (buttonLabels[i].equals("=")) {
                buttons[i].setBackground(Color.ORANGE);
                buttons[i].setForeground(Color.WHITE);
            } else {
                buttons[i].setBackground(Color.DARK_GRAY);
                buttons[i].setForeground(Color.WHITE);
            }

            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9]+") || command.equals("00") || command.equals(".")) {
            expression += command;
        } else if (command.equals("AC")) {
            expression = "";
        } else if (command.equals("C")) {
            if (!expression.isEmpty()) {
                expression = expression.substring(0, expression.length() - 1);
            }
        } else if (command.equals("%")) {
            try {
                expression = String.valueOf(Double.parseDouble(expression) / 100);
            } catch (Exception ex) {
                expression = "Error";
            }
        } else if (command.equals("=")) {
            try {
                expression = evaluateExpression(expression);
            } catch (Exception ex) {
                expression = "Error";
            }
        } else {
            expression += " " + command + " ";
        }
        display.setText(expression);
    }

    private String evaluateExpression(String exp) {
        try {
            exp = exp.replace("×", "*").replace("÷", "/");
            return String.valueOf(evaluate(exp));
        } catch (Exception e) {
            return "Error";
        }
    }

    private double evaluate(String expression) {
        String[] tokens = expression.split(" ");
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();
        
        for (String token : tokens) {
            if (token.matches("[0-9.]+")) {
                numbers.push(Double.parseDouble(token));
            } else {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token.charAt(0))) {
                    double b = numbers.pop();
                    double a = numbers.pop();
                    numbers.push(applyOperator(operators.pop(), a, b));
                }
                operators.push(token.charAt(0));
            }
        }

        while (!operators.isEmpty()) {
            double b = numbers.pop();
            double a = numbers.pop();
            numbers.push(applyOperator(operators.pop(), a, b));
        }
        return numbers.pop();
    }

    private int precedence(char operator) {
        switch (operator) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            default: return 0;
        }
    }

    private double applyOperator(char operator, double a, double b) {
        switch (operator) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return b != 0 ? a / b : 0;
            default: return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
    }
}
