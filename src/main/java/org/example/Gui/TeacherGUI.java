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
        frame.setSize(1760, 990);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel courseLabel = new JLabel("Enter Course Name:");
        JTextField courseNameField = new JTextField(20);

        JButton addCourseButton = new JButton("Add Course");

        JLabel studentLabel = new JLabel("List of Students:");

        JTextArea studentTextArea = new JTextArea(10, 20);
        studentTextArea.setEditable(false);

        panel.add(courseLabel);
        panel.add(courseNameField);
        panel.add(addCourseButton);
        panel.add(studentLabel);
        panel.add(studentTextArea);

        frame.add(panel);
        frame.setVisible(true);

        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName = courseNameField.getText();
                insertCourse(courseName);
                courseNameField.setText("");
            }
        });

        // Populate the student list initially
        updateStudentList(studentTextArea);
    }

    private void insertCourse(String courseName) {
        try {
            String insertSQL = "INSERT INTO students (dersadi) VALUES (courseNameField)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, courseName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateStudentList(JTextArea studentTextArea) {
        try {
            String selectSQL = "SELECT name, ogrno, ilgialani FROM students where userrole = 'ogrenci'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSQL);

            List<String> studentList = new ArrayList<>();

            while (resultSet.next()) {
                String Oname = resultSet.getString("name");
                int ogrno = resultSet.getInt("ogrno");
                String ilgialanı = resultSet.getString("ilgialani");
                studentList.add("Name: " + Oname + ", Number: " + ogrno + ", ECTS Points: " + ilgialanı);
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
