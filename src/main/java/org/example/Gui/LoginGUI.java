package org.example.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(20);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
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
        panel.add(loginButton, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(registerButton, gbc);

        // Inside the ActionListener for the login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:postgresql://localhost:5432/dersEslestirme";
                String user = "postgres";
                String password = "1234";

                try (Connection connection = DriverManager.getConnection(url, user, password)) {
                    String query = "SELECT name, ogrno, userrole FROM public.users WHERE name = ? AND ogrno = ?";

                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        // Get the password as a character array
                        char[] passwordChars = passField.getPassword();
                        String passwordStr = new String(passwordChars);
                        int passwordInt = Integer.parseInt(passwordStr);

                        preparedStatement.setString(1, userField.getText()); // Set the username from the input field
                        preparedStatement.setInt(2, passwordInt);

                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            if (resultSet.next()) {
                                // Retrieve the values from the database
                                String name = resultSet.getString("name");
                                int ogrno = resultSet.getInt("ogrno");
                                String userrole = resultSet.getString("userrole");

                                if (name.equals(userField.getText()) && ogrno == passwordInt) {
                                    // Check the userrole and open the corresponding GUI
                                    if ("öğrenci".equals(userrole)) {
                                        OgrenciGUI newFrame = new OgrenciGUI();
                                    } else if ("öğretmen".equals(userrole)) {
                                        TeacherGUI newTFrame = new TeacherGUI();
                                    } else if ("yönetici".equals(userrole)) {
                                        YoneticiGUI newYFrame = new YoneticiGUI();
                                    }

                                    JOptionPane.showMessageDialog(frame, "Login successful!");
                                } else {
                                    // Login failed
                                    JOptionPane.showMessageDialog(frame, "Login failed. Please check your credentials.");
                                }
                            } else {
                                // No results found for the provided username and password
                                JOptionPane.showMessageDialog(frame, "Login failed. User not found or credentials incorrect.");
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage());
                }

                // Clear the password field
                passField.setText("");
            }
        });


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationGUI rFrame = new RegistrationGUI();
                deneme bir = new deneme();
                bir.setVisible(true);
            }
        });
        frame.setVisible(true);
    }
}
