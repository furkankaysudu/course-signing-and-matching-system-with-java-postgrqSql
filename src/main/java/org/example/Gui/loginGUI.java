package org.example.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class loginGUI {
    public static void main(String[] args) {
        // Create a JFrame (the main window)
        JFrame frame = new JFrame("Login");
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
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Center and add components using GridBagConstraints
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
        panel.add(loginButton, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(registerButton, gbc);

        // Create an ActionListener for the login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String url = "jdbc:postgresql://localhost:5432/dersEslestirme";
                String user = "postgres";
                String password = "1234";
                String number = userField.getText();

                try (Connection connection = DriverManager.getConnection(url, user, password)) {
                    String username = "postgres"; // Replace with the actual username

                    // Create a SQL query to retrieve the name and dealCount
                    String query = "SELECT number FROM public.students";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        // preparedStatement.setString(1, username);
                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            if (resultSet.next()) {
                                // Retrieve the values from the database
                                String name = resultSet.getString("name");

                                if (name.equals(number) && password.equals("1234")) {
                                    userPanelGUI newFrame = new userPanelGUI();
                                    JOptionPane.showMessageDialog(frame, "Login successful!");

                                } else {
                                    JOptionPane.showMessageDialog(frame, "Login failed. Please check your credentials.");
                                }

                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage());
                }
                // Implement your login logic here
                // Example: Check if the username and password are correct



                // Clear the password field
                passField.setText("");
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                RegistrationGUI rFrame = new RegistrationGUI();
            }
        });

        // Display the frame
        frame.setVisible(true);
    }
}
