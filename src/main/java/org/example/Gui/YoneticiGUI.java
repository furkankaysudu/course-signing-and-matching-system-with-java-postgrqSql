package org.example.Gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class YoneticiGUI {

    private Connection connection;
    private JFrame frame;
    private JTextArea studentsTextArea;
    private JTextArea teachersTextArea;

    public YoneticiGUI() {
        try {
            // Initialize the database connection
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dersEslestirme", "postgres", "1234");

            createAndShowGUI();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createAndShowGUI() {
        frame = new JFrame("YoneticiGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Top Panel for User Info
        JLabel userInfoLabel = new JLabel("Name: John Doe | Number: 12345");
        panel.add(userInfoLabel, BorderLayout.NORTH);

        // Middle Panel for Students and Teachers Lists
        JPanel middlePanel = new JPanel(new GridLayout(1, 2));
        studentsTextArea = new JTextArea(20, 30);
        teachersTextArea = new JTextArea(20, 30);
        middlePanel.add(new JScrollPane(studentsTextArea));
        middlePanel.add(new JScrollPane(teachersTextArea));
        panel.add(middlePanel, BorderLayout.CENTER);

        // Bottom Panel for Buttons
        JPanel bottomPanel = new JPanel();
        JButton refreshButton = new JButton("onayla");
        bottomPanel.add(refreshButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Refresh the student and teacher lists
                updateStudentList(studentsTextArea);
                updateTeacherList(teachersTextArea);
            }
        });

        // Initial data loading
        updateStudentList(studentsTextArea);
        updateTeacherList(teachersTextArea);
    }

    private void updateStudentList(JTextArea textArea) {
        // Retrieve and display student data from the database
        textArea.setText(getDataFromDatabase("users"));
    }

    private void updateTeacherList(JTextArea textArea) {
        // Retrieve and display teacher data from the database
        textArea.setText(getDataFromDatabase("users"));
    }

    private String getDataFromDatabase(String tableName) {
        try {
            String selectSQL = "SELECT name, number FROM " + tableName;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSQL);

            List<String> dataList = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int number = resultSet.getInt("number");
                dataList.add("Name: " + name + " | Number: " + number);
            }

            return String.join("\n", dataList);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error fetching data from the database.";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new YoneticiGUI();
        });
    }
}
