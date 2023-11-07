package org.example.Gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Registration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(100);

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(100);

        JLabel numberLabel = new JLabel("ögrNo/sicil no");
        JTextField numberField = new JTextField(100);

        JCheckBox ogrenciBox = new JCheckBox("öğrenci");
        JCheckBox ogretmenBox = new JCheckBox("öğretmen");
        JCheckBox yoneticiBox = new JCheckBox("yönetici");

        JButton registerButton = new JButton("kayıt ol");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);
        gbc.gridy = 1;
        panel.add(passLabel, gbc);
        gbc.gridy = 2;
        panel.add(numberLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(userField, gbc);
        gbc.gridy = 1;
        panel.add(passField, gbc);
        gbc.gridy = 2;
        panel.add(numberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(ogrenciBox, gbc);
        gbc.gridx = 1;
        panel.add(ogretmenBox, gbc);
        gbc.gridx = 2;
        panel.add(yoneticiBox, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(registerButton, gbc);

        // Action listener for the "kayıt ol" button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:postgresql://localhost:5432/dersEslestirme";
                String user = "postgres";
                String password = "1234";

                String name = userField.getText();
                int userPassword = Integer.parseInt(new String(passField.getPassword()));
                int userNumber = Integer.parseInt(numberField.getText());

                // Determine the role based on checkbox selection
                String userrole = "";
                if (ogrenciBox.isSelected()) {
                    userrole = "öğrenci";
                } else if (ogretmenBox.isSelected()) {
                    userrole = "öğretmen";
                } else if (yoneticiBox.isSelected()) {
                    userrole = "yönetici";
                }

                try (Connection connection = DriverManager.getConnection(url, user, password)) {
                    String query = "INSERT INTO public.users (name, password, number, userrole) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, name);
                        preparedStatement.setInt(2, userPassword);
                        preparedStatement.setInt(3, userNumber);
                        preparedStatement.setString(4, userrole);

                        int rowsInserted = preparedStatement.executeUpdate();
                        if (rowsInserted > 0) {
                            JOptionPane.showMessageDialog(frame, "Registration successful!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Registration failed.");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }
}
