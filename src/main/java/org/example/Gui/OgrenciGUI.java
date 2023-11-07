package org.example.Gui;
import org.example.Controller.OCRUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OgrenciGUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel centerLeftPanel;
    private JPanel centerRightPanel;
    private JPanel bottomPanel;
    private JLabel nameLabel;
    private JLabel lastNameLabel;
    private JLabel ogrNoLabel;
    private JLabel anlasmatalepsayisiLabel;
    private JTextArea pdfTextArea;
    private JList<String> teacherList;
    private DefaultListModel<String> teacherListModel;
    private JButton uploadButton;
    private boolean pdfUploaded = false;

    public OgrenciGUI() {
        frame = new JFrame("User Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1760, 990);
        frame.setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Top Panel for User Information
        topPanel = new JPanel(new GridLayout(4, 1));
        nameLabel = new JLabel("Name: John"); // Replace with actual name
        lastNameLabel = new JLabel("Last Name: Doe"); // Replace with actual last name
        ogrNoLabel = new JLabel("Öğrenci No: 12345"); // Replace with actual Öğrenci No
        anlasmatalepsayisiLabel = new JLabel("Anlaşma Telesi Sayısı: 5"); // Replace with actual count
        topPanel.add(nameLabel);
        topPanel.add(lastNameLabel);
        topPanel.add(ogrNoLabel);
        topPanel.add(anlasmatalepsayisiLabel);

        // Center Left Panel for PDF Text
        centerLeftPanel = new JPanel(new BorderLayout());
        pdfTextArea = new JTextArea();
        pdfTextArea.setEditable(false);
        JScrollPane textScrollPane = new JScrollPane(pdfTextArea);
        centerLeftPanel.add(textScrollPane, BorderLayout.CENTER);

        // Center Right Panel for Teacher List
        centerRightPanel = new JPanel(new BorderLayout());
        teacherListModel = new DefaultListModel<>();
        teacherList = new JList<>(teacherListModel);
        JScrollPane teacherScrollPane = new JScrollPane(teacherList);
        centerRightPanel.add(teacherScrollPane, BorderLayout.CENTER);

        // Bottom Panel for Upload Button
        bottomPanel = new JPanel();
        uploadButton = new JButton("Upload PDF File");
        bottomPanel.add(uploadButton);

        // Add Panels to the Main Panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerLeftPanel, BorderLayout.CENTER);
        mainPanel.add(centerRightPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Upload Button ActionListener
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:postgresql://localhost:5432/dersEslestirme";
                String user = "postgres";
                String password = "1234";

                try (Connection connection = DriverManager.getConnection(url, user, password)) {
                    String username = "postgres"; // Replace with the actual username

                    // Create a SQL query to retrieve the name and dealCount
                    String query = "SELECT * FROM public.users";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                       // preparedStatement.setString(1, username);
                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            if (resultSet.next()) {
                                // Retrieve the values from the database
                                String name = resultSet.getString("name");
                                int dealCount = resultSet.getInt("anlasmatalepsayisi");

                                // Display the retrieved data
                                nameLabel.setText("User: " + name);
                                anlasmatalepsayisiLabel.setText("Deal Count: " + dealCount);
                            } else {
                                JOptionPane.showMessageDialog(frame, "User not found.");
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage());
                }
                if (!pdfUploaded) {
                    JFileChooser fileChooser = new JFileChooser();
                    int returnValue = fileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        pdfUploaded = true;
                        File selectedFile = fileChooser.getSelectedFile();

                        // Use OCR to extract text from the PDF
                        String extractedText = OCRUtil.extractTextFromPDFWithOCR(selectedFile);

                        // Display the extracted text in the pdfTextArea
                        pdfTextArea.setText(extractedText);

                        // ... (rest of the code)
                    }
                }
            }
        });

        frame.add(mainPanel);
        frame.setVisible(true);

        // Load teacher names from the database
        loadTeacherNames();
    }

    private void loadTeacherNames() {
        try {
            String url = "jdbc:postgresql://localhost:5432/dersEslestirme";
            String user = "postgres";
            String password = "1234";

            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                String query = "SELECT name FROM public.users WHERE userrole = 'ögretmen'";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String teacherName = resultSet.getString("name");
                        teacherListModel.addElement(teacherName);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading teacher names: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OgrenciGUI());
    }
}