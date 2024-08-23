package calculator;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Calculator extends JFrame implements ActionListener {

    private JTextField display;
    private String[] buttonLabels = {
            "%", "√x", "x²", "C", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "="
    };
    private JButton[] buttons = new JButton[buttonLabels.length];
    private String currentOperator;
    private double firstOperand, secondOperand;
    private boolean isOperatorClicked = false;

    public Calculator() {
        // Set up the frame
        setTitle("Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        display.setBackground(Color.BLACK);
        display.setForeground(Color.GREEN);
        add(display, BorderLayout.NORTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBackground(Color.DARK_GRAY);
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            buttons[i].setBackground(Color.GRAY);
            buttons[i].setForeground(Color.BLACK);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }
        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Handle number and dot input
        if (command.matches("\\d") || command.equals(".")) {
            if (isOperatorClicked) {
                display.setText("");
                isOperatorClicked = false;
            }
            display.setText(display.getText() + command);
        } 
        // Handle operator input
        else if (command.equals("+") || command.equals("-") || command.equals("×") || command.equals("÷")) {
            firstOperand = Double.parseDouble(display.getText());
            currentOperator = command;
            isOperatorClicked = true;
        } 
        // Handle special functions
        else if (command.equals("=")) {
            secondOperand = Double.parseDouble(display.getText());
            switch (currentOperator) {
                case "+":
                    display.setText(String.valueOf(firstOperand + secondOperand));
                    break;
                case "-":
                    display.setText(String.valueOf(firstOperand - secondOperand));
                    break;
                case "×":
                    display.setText(String.valueOf(firstOperand * secondOperand));
                    break;
                case "÷":
                    display.setText(String.valueOf(firstOperand / secondOperand));
                    break;
            }
        } else if (command.equals("C")) {
            display.setText("");
            firstOperand = 0;
            secondOperand = 0;
            currentOperator = "";
        } else if (command.equals("%")) {
            double currentValue = Double.parseDouble(display.getText());
            display.setText(String.valueOf(currentValue / 100));
        } 
        // Handle square root operation
        else if (command.equals("√x")) {
            display.setText(String.valueOf(Math.sqrt(Double.parseDouble(display.getText()))));
        } 
        // Handle square operation
        else if (command.equals("x²")) {
            display.setText(String.valueOf(Math.pow(Double.parseDouble(display.getText()), 2)));
        }
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.setVisible(true);
    }
}
