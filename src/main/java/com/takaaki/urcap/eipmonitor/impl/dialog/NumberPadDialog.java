package com.takaaki.urcap.eipmonitor.impl.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NumberPadDialog extends JDialog {
    private StringBuilder currentValue = new StringBuilder();
    private boolean isNegative = false;
    private boolean hasDecimal = false;
    private boolean isValid = true;  // Track if the input is valid
    private String resultValue = "";  // Store the result (valid or not)

    public NumberPadDialog(JFrame parent) {
        super(parent, "Enter Value", true);
        setLayout(new BorderLayout());

        // Display field for input
        final JTextField display = new JTextField();
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        // Panel for the number pad buttons
        JPanel buttonPanel = new JPanel(new GridLayout(5, 3));  // 4 rows, 3 columns

        // Add number buttons (7-9, 4-6, 1-3)
        addButton(buttonPanel, "7", display);
        addButton(buttonPanel, "8", display);
        addButton(buttonPanel, "9", display);
        addButton(buttonPanel, "4", display);
        addButton(buttonPanel, "5", display);
        addButton(buttonPanel, "6", display);
        addButton(buttonPanel, "1", display);
        addButton(buttonPanel, "2", display);
        addButton(buttonPanel, "3", display);
        
        // Add decimal, 0, +-, and +- toggle buttons
        addButton(buttonPanel, ".", display);
        addButton(buttonPanel, "0", display);
        addToggleSignButton(buttonPanel, display);
        addBackspaceButton(buttonPanel, display);
        
        // Add the number pad buttons to the panel
        add(buttonPanel, BorderLayout.CENTER);

        // Add OK and Cancel buttons
        JPanel controlPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultValue = currentValue.toString();
                // Validate the input (before accepting the value)
                if (isValidInteger(resultValue) || isValidFloat(resultValue)) {
                    isValid = true;
                } else {
                    isValid = false;
                    resultValue = "";  // Reset result if invalid
                    // Show an error message if the input is invalid
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    setVisible(true);
                }
                setVisible(false);
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultValue = "";
                setVisible(false);
            }
        });
        controlPanel.add(okButton);
        controlPanel.add(cancelButton);
        add(controlPanel, BorderLayout.SOUTH);

        setSize(300, 400);
        setLocationRelativeTo(parent);
    }

    // Return the result value after validation
    public String getResult() {
        return resultValue;
    }

    // Validate the input as an integer
    private boolean isValidInteger(String value) {
        return value.matches("-?\\d+");  // Match an optional minus sign followed by digits
    }

    // Validate the input as a float
    private boolean isValidFloat(String value) {
        return value.matches("-?\\d*\\.?\\d+");  // Match an optional minus sign, digits, and optional decimal
    }

    // Helper method to add a number button
    private void addButton(JPanel panel, final String label, final JTextField display) {
        JButton button = new JButton(label);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentValue.append(label);  // Append the label (number) to the input
                display.setText(currentValue.toString());  // Update the display
            }
        });
        panel.add(button);
    }

    // Helper method for the backspace button
    private void addBackspaceButton(JPanel panel, final JTextField display) {
        JButton backspaceButton = new JButton("←");
        backspaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentValue.length() > 0) {
                    currentValue.deleteCharAt(currentValue.length() - 1);  // Remove last character
                    display.setText(currentValue.toString());
                }
            }
        });
        panel.add(backspaceButton);
    }

    // Helper method for the toggle sign button
    private void addToggleSignButton(JPanel panel, final JTextField display) {
        JButton toggleSignButton = new JButton("+-");
        toggleSignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentValue.length() > 0) {
                    if (isNegative) {
                        currentValue.deleteCharAt(0);  // Remove the negative sign
                    } else {
                        currentValue.insert(0, "-");  // Prepend negative sign
                    }
                    isNegative = !isNegative;  // Toggle the sign state
                    display.setText(currentValue.toString());
                }
            }
        });
        panel.add(toggleSignButton);
    }
}
