package com.javateam.libramanagerjava;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;


public class FileEditorApp extends JFrame {
    private JTextField filePathField;
    private JTextArea textArea;
    private File selectedFile;
    private JButton saveButton;
    private File currentFile;

    public FileEditorApp() {
        setTitle("Trình chỉnh sửa file");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        // === Giao diện 1 ===
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton chooseFileBtn = new JButton("Chọn File");
        filePathField = new JTextField();
        filePathField.setEditable(false);
        topPanel.add(chooseFileBtn, BorderLayout.WEST);
        topPanel.add(filePathField, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // === Giao diện 2 ===
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        JButton saveButton = new JButton("Lưu ");
        topPanel.add(saveButton, BorderLayout.EAST);

        // === Sự kiện ===
        chooseFileBtn.addActionListener(e -> chooseFile());
        saveButton.addActionListener(e -> saveFileToFolder());

        setVisible(true);
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Chỉ nhận file .docx, .md, .txt", "docx", "md", "txt");
        chooser.setFileFilter(filter);

        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            filePathField.setText(selectedFile.getAbsolutePath());
            // saveFileToFolder();

        String path = selectedFile.getAbsolutePath();
        try {
                if (path.endsWith(".txt") || path.endsWith(".md")) {
                    String content = Files.readString(selectedFile.toPath(), StandardCharsets.UTF_8);
                    textArea.setText(content);
                } else if (path.endsWith(".docx")) {
                    textArea.setText(readDocx(selectedFile));
                } else {
                    JOptionPane.showMessageDialog(this, "Định dạng không được hỗ trợ!");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi mở file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
        
        private String readDocx(File file) {
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument doc = new XWPFDocument(fis)) {
            for (XWPFParagraph para : doc.getParagraphs()) {
                sb.append(para.getText()).append("\n");
            }
        } catch (Exception e) {
            sb.append("Lỗi đọc docx: ").append(e.getMessage());
        }
        return sb.toString();
    }
    
    private String saveFileToFolder() {
        // if (selectedFile == null) {
        //     JOptionPane.showMessageDialog(this, "Chưa chọn file!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        //     return;
        // }

        // JFileChooser dirChooser = new JFileChooser();
        // dirChooser.setDialogTitle("Chọn thư mục để lưu file");
        // dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // int userSelection = dirChooser.showSaveDialog(this);
        // if (userSelection == JFileChooser.APPROVE_OPTION) {
        //     File folder = dirChooser.getSelectedFile();

            // Cho người dùng nhập tên file
            // String fileName = JOptionPane.showInputDialog(this, "Nhập tên file:", "copy_" + System.currentTimeMillis());
            // if (fileName == null || fileName.trim().isEmpty()) {
            //     return; // Người dùng bấm Cancel hoặc không nhập
            // }

            // if (!fileName.endsWith(".txt")) {
            //     fileName += ".txt";
            // }
            String folder = "D:/PHUONG"; // Thư mục lưu file
            String fileName = System.currentTimeMillis() + ".txt"; // Tạo tên file tự động
            File newFile = new File(folder, fileName);
            try {
                Files.write(newFile.toPath(), textArea.getText().getBytes("UTF-8"));
                JOptionPane.showMessageDialog(this,
                        "Đã lưu file tại:\n" + newFile.getAbsolutePath(),
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        return newFile.getAbsolutePath();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Không thể lưu file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
// Compare this snippet from src/main/java/com/javateam/libramanagerjava/FileEditorApp.java:
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileEditorApp().setVisible(true));
    }
}
