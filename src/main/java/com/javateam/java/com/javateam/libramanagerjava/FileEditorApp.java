import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FileEditorApp extends JFrame {
    private JTextField filePathField;
    private JTextArea textArea;
    private File selectedFile;

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

        JButton saveBtn = new JButton("Lưu đè file");
        add(saveBtn, BorderLayout.SOUTH);

        // === Sự kiện ===
        chooseFileBtn.addActionListener(e -> chooseFile());
        saveBtn.addActionListener(e -> overwriteFile());

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
            loadFileContent();
        }
    }

    private void loadFileContent() {
        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
            textArea.setText("");
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void overwriteFile() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Chưa chọn file!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
            writer.write(textArea.getText());
            JOptionPane.showMessageDialog(this, "Đã lưu đè thành công!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FileEditorApp::new);
    }
}

