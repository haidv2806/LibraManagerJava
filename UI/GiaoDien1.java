import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GiaoDien1 extends JFrame {
    public GiaoDien1() {
        setTitle("Giao diện 1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JButton chooseButton = new JButton("Hãy chọn file");
        JLabel fileLabel = new JLabel("Chỉ nhận file: .docx, .md, .txt");

        chooseButton.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            int result = fc.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fc.getSelectedFile();
                String path = selectedFile.getAbsolutePath();
                if (path.endsWith(".docx") || path.endsWith(".md") || path.endsWith(".txt")) {
                    new GiaoDien2(selectedFile);
                    this.dispose(); // đóng giao diện 1
                } else {
                    JOptionPane.showMessageDialog(this, "Chỉ hỗ trợ file .docx, .md, .txt");
                }
            }
        });

        setLayout(new FlowLayout());
        add(chooseButton);
        add(fileLabel);

        setVisible(true);
    }
}
