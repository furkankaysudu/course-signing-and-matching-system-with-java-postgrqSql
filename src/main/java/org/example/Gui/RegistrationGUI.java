package org.example.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationGUI {
    // Create a JFrame (the main window)
    public static void main(String[] args) {
    JFrame frame = new JFrame("Rgistration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200); // Increase the size
        frame.setLocationRelativeTo(null); // Center the window on the screen

        // Create a JPanel to hold components
        JPanel panel = new JPanel();
        frame.add(panel);

        // Set a layout manager (e.g., GridBagLayout) to center components
        panel.setLayout(new GridBagLayout());

    // Create GridBagConstraints to center components
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);

    // Create and add components
    JLabel userLabel = new JLabel("Username:");
    JTextField userField = new JTextField(20);
    JLabel passLabel = new JLabel("Password:");
    JPasswordField passField = new JPasswordField(20);
    JButton registerButton = new JButton("Register");

    gbc.gridx = 0;
    gbc.gridy = 0;
        panel.add(userLabel, gbc);
    gbc.gridy = 1;
        panel.add(passLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = 0;
        panel.add(userField, gbc);
    gbc.gridy = 1;
        panel.add(passField, gbc);

    gbc.gridwidth = 2;
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(registerButton, gbc);

    frame.setVisible(true);
    }

}
