package com.javateam.libramanagerjava;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GiaoDien1 extends  JFrame {
    public GiaoDien1() {
        setTitle("Libra Manager - Document Viewer");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);

        JButton chooseButton = new JButton("Chọn File");
        JLabel fileLabel = new JLabel("Chỉ nhận file : .docx, .md , .txt");

        chooseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                java.io.File selectedFile = fileChooser.getSelectedFile();
                fileLabel.setText("Chon file: " + selectedFile.getAbsolutePath());
                new GiaoDien2(selectedFile);// Open the document viewer with the selected file
            } else {
                fileLabel.setText("Khong chon file nao");
            }
        });

        setLayout(new FlowLayout());
        add(chooseButton);
        add(fileLabel);
        setVisible(true);
    }
}

