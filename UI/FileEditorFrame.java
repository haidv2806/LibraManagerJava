import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;    

public class FileEditorFrame{
    private JFrame frame;
    private JTextArea textArea;
    private JTextField fliePathField;
    private JTextField savePathField;
    private File selectedFile;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileEditorFrame editor = new FileEditorFrame();
            editor.createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        frame = new JFrame("File Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Giao diện chọn  file
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        filePathField = new JTextField();
        filePathField.setEditable(false);
        JButton browseButton = new JButton("Chọn File");
        topPanel.add(new JLabel("Chọn file: "), BorderLayout.WEST);
        topPanel.add(filePathField, BorderLayout.CENTER);  
        topPanel.add(browseButton, BorderLayout.EAST);

        //Chỉnh sửa nội dung file
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);

        //Giao diện lưu file
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        savePathField = new JTextField();
        JButton saveButton = new JButton("Lưu thành .txt");
        bottomPanel.add(new JLabel("Lưu file: "), BorderLayout.WEST);
        bottomPanel.add(savePathField, BorderLayout.CENTER);   
        bottomPanel.add(saveButton, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        //Thêm sự kiện cho các nút
        browseButton.addActionListener(e -> chooseFile());
        saveButton.addActionListener(e -> saveFileAsTxt());

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text/Markdown/Word Files", "txt", "md","docx");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            loadFileContent();
        }
    }

    private void loadFileContent() {
        try{
            String fileName = selectedFile.getName().toLowerCase();
            if (fileName.endsWith(".txt") || fileName.endsWith(".md")) {
                String content = new String(Files.readAllBytes(selectedFile.toPath()));
                textArea.setText(content);
            } else if (flieName.endsWith(".docx")){
                textArea.setText("Chức năng mở file .docx chưa được hỗ trợ");
            }else {
                textArea.setText("Đinh dạng file không được hỗ trợ");
            }
        }catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Lỗi khi đọc file: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFileAsTxt(){
        String fileName = savePathField.getText().trim();
        if (fileName.isEmpty()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file");
            fileChooser.setSelecctedFile(new File("newfile.txt"));
            int result = fileChooser.showSaveDialog(frame);

            if (result == JFileChooser.APPROVE_OPTION) {
                savePath = fileChooser.getSelectedFile().getAbsolutePath();
                savePathField.setText(savePath);
            } else {
                return; // Người dùng hủy lưu
            }
        }

        try{
            FileHandle.writeFile(savePath, textArea.getText());
            JOptionPane.showMessageDialog(frame, "File đã được lưu thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Lỗi khi lưu file: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
