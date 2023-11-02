package org.example.Gui;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.example.Controller.OCRUtil;
import org.example.Model.Student;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class userPanelGUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JLabel dealCountLabel;
    private JButton uploadButton;
    private JTextArea pdfTextArea;
    private JTable dataTable;
    private JScrollPane tableScrollPane;
    private boolean pdfUploaded = false;

    public userPanelGUI() {
        frame = new JFrame("User Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1760, 990);
        frame.setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        //Student ogr1 = new Student();

        // Top Panel for Name and Deal Count
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        nameLabel = new JLabel("User: John Doe"); // Replace with actual user name
        dealCountLabel = new JLabel("Deal Count: 5"); // Replace with actual deal count
        topPanel.add(nameLabel);
        topPanel.add(dealCountLabel);

        // Center Panel for PDF and Table
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        pdfTextArea = new JTextArea();
        pdfTextArea.setEditable(false);
        tableScrollPane = new JScrollPane();
        centerPanel.add(pdfTextArea, BorderLayout.CENTER);
        centerPanel.add(tableScrollPane, BorderLayout.SOUTH);

        // Bottom Panel for Upload Button
        JPanel bottomPanel = new JPanel();
        uploadButton = new JButton("Upload PDF File");
        bottomPanel.add(uploadButton);

        // Add Panels to the Main Panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
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
                    String query = "SELECT * FROM public.students";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                       // preparedStatement.setString(1, username);
                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            if (resultSet.next()) {
                                // Retrieve the values from the database
                                String name = resultSet.getString("name");
                                int dealCount = resultSet.getInt("anlasmatalepsayisi");

                                // Display the retrieved data
                                nameLabel.setText("User: " + name);
                                dealCountLabel.setText("Deal Count: " + dealCount);
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new userPanelGUI());
    }
}
