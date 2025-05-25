/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaceDung;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author admin
 */
public class Page4 extends JFrame{
    private JFrame parent;
    private JTextArea contentArea;
    private JTextField nameField;
    private String volumeId; // Lưu ID tập để xác định chế độ chỉnh sửa

    public Page4(JFrame parent, String volumeId) {
        this.parent = parent;
        this.volumeId = volumeId;
        setTitle(volumeId == null ? "Trang 4 - Thêm tập mới" : "Trang 4 - Sửa tập");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JButton closeButton = new JButton("X");
        closeButton.setForeground(Color.RED);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.addActionListener(e -> {
            parent.setVisible(true);
            dispose();
        });
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(closeButton);
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Tên tập:"));
        nameField = new JTextField(20);
        namePanel.add(nameField);
        formPanel.add(namePanel);
        
        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filePanel.add(new JLabel("Chọn file:"));
        JButton chooseFileButton = new JButton("Chọn file");
        chooseFileButton.setPreferredSize(new Dimension(100, 30));
        chooseFileButton.setBackground(Color.GREEN);
        
        chooseFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                nameField.setText(selectedFile.getName());
                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    contentArea.setText("");
                    String line;
                    while ((line = reader.readLine()) != null) {
                        contentArea.append(line + "\n");
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi đọc file: " + ex.getMessage());
                }
            }
        });
        
        filePanel.add(chooseFileButton);
        formPanel.add(filePanel);
        
        contentArea = new JTextArea("Khu vực sửa nội dung file", 10, 20);
        contentArea.setLineWrap(true);
        formPanel.add(new JScrollPane(contentArea));
        
        JButton confirmButton = new JButton(volumeId == null ? "Xác nhận thêm tập" : "Xác nhận sửa tập");
        confirmButton.setBackground(Color.GREEN);
        
        confirmButton.addActionListener(e -> {
            String volumeName = nameField.getText().trim();
            
            if (volumeName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền tên tập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (volumeId == null) {
                // Thêm tập mới
                ((Page3) parent).addVolume(volumeName, "28/06/26");
                JOptionPane.showMessageDialog(this, "Đã thêm tập: " + volumeName);
            } else {
                // Cập nhật tập hiện có
                ((Page3) parent).updateVolume(volumeId, volumeName, "28/06/26");
                JOptionPane.showMessageDialog(this, "Đã cập nhật tập: " + volumeName);
            }
            
            parent.setVisible(true);
            dispose();
        });
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(confirmButton);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(formPanel), BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    public void setVolumeData(String volumeName) {
        nameField.setText(volumeName);
    }
}
