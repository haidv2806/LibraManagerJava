package com.javateam.libramanagerjava;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GiaoDien2 extends JFrame{
    JTextArea textArea;
    JTextField textField;

    public GiaoDien2(File file) {
        setTitle("Libra Manager - Document Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        add(mainPanel, BorderLayout.CENTER);
        textArea = new JTextArea(15, 50);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textField = new JTextField(file.getAbsolutePath(), 50);
        JButton saveButton = new JButton("Luu ");

        add(textArea);
        add(textField);

        //Các lực chọn a, b, c,...
        JPanel optionJPanel = new JPanel();
        optionJPanel.add(new JLabel("a"));
        optionJPanel.add(new JLabel("b"));
        optionJPanel.add(new JLabel("c"));
        optionJPanel.add(new JLabel("..."));

        //Cho phép sửa
        JButton ediButton = new JButton("Cho phép sửa");
        ediButton.addActionListener(e -> {
            // Action to perform when the edit button is clicked
            JOptionPane.showMessageDialog(this, "Chức năng sửa đã được kích hoạt!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        setLayout(null); // Use absolute positioning
        textArea.setBounds(10, 10, 760, 500);
        textField.setBounds(10, 520, 760, 30);
        saveButton.setBounds(10, 560, 100, 30);
        mainPanel.setBounds(10, 10, 760, 500);
        mainPanel.add(optionJPanel);
        mainPanel.add(ediButton);
        add(scrollPane);
        add(saveButton);
        saveButton.addActionListener(e -> {
            // Action to perform when the save button is clicked
            try (FileWriter writer = new FileWriter(textField.getText())) {
                writer.write(textArea.getText());
                JOptionPane.showMessageDialog(this, "Da luu file", "Success", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Loi khi luu file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        setVisible(true);

    }
}

