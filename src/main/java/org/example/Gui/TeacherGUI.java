package org.example.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherGUI {

    private Connection connection;

    public TeacherGUI() {
        try {
            // Initialize the database connection
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dersEslestirme", "postgres", "1234");

            createAndShowGUI();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Teacher Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 480);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Top Panel for User Information
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel nameLabel = new JLabel("Name: John"); // Replace with actual name
        JLabel lastNameLabel = new JLabel("Last Name: Doe"); // Replace with actual last name
        JLabel sicilnoLabel = new JLabel("Sicil No: 12345"); // Replace with actual Sicil No
        topPanel.add(nameLabel);
        topPanel.add(lastNameLabel);
        topPanel.add(sicilnoLabel);

        // Middle Panel for Course and ilgialani Entry
        JPanel middlePanel = new JPanel(new GridLayout(2, 2));
        JLabel courseLabel = new JLabel("Enter Ders Adı:");
        JTextField courseNameField = new JTextField(20);
        JButton addCourseButton = new JButton("Add Course");
        JLabel ilgialaniLabel = new JLabel("Enter İlgialanı:");
        JTextField ilgialaniField = new JTextField(20);
        JButton addIlgialaniButton = new JButton("Add İlgialanı");

        middlePanel.add(courseLabel);
        middlePanel.add(courseNameField);
        middlePanel.add(addCourseButton);
        middlePanel.add(ilgialaniLabel);
        middlePanel.add(ilgialaniField);
        middlePanel.add(addIlgialaniButton);

        // Bottom Panel for Student List
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JTextArea studentTextArea = new JTextArea(10, 20);
        studentTextArea.setEditable(false);

        bottomPanel.add(new JScrollPane(studentTextArea), BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(middlePanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName = courseNameField.getText();
                insertCourse(courseName);
                courseNameField.setText("");
                updateStudentList(studentTextArea);
            }
        });

        addIlgialaniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ilgialani = ilgialaniField.getText();
                insertIlgialani(ilgialani);
                ilgialaniField.setText("");
                updateStudentList(studentTextArea);
            }
        });

        // Populate the student list initially
        updateStudentList(studentTextArea);
    }

    private void insertCourse(String courseName) {
        try {
            String insertSQL = "INSERT INTO users (dersadi) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, courseName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertIlgialani(String ilgialani) {
        try {
            String insertSQL = "INSERT INTO users (ilgialani) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, ilgialani);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateStudentList(JTextArea studentTextArea) {
        try {
            String selectSQL = "SELECT name FROM users WHERE userrole = 'öğrenci'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSQL);

            List<String> studentList = new ArrayList<>();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                studentList.add(name);
            }

            studentTextArea.setText(String.join("\n", studentList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TeacherGUI();
        });
    }
}
