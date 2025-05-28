import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GiaoDien2 extends JFrame {
    JTextArea textArea;
    JTextField txtDuongDan;

    public GiaoDien2(File file) {
        setTitle("Giao diện 2");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        textArea = new JTextArea(15, 40);
        JScrollPane scrollPane = new JScrollPane(textArea);
        txtDuongDan = new JTextField(file.getAbsolutePath(), 30);
        JButton btnLuu = new JButton("Lưu");

        btnLuu.addActionListener(e -> {
            try (FileWriter writer = new FileWriter(txtDuongDan.getText())) {
                writer.write(textArea.getText());
                JOptionPane.showMessageDialog(this, "Đã lưu file thành công!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu file!");
            }
        });

        // Đọc nội dung file vào text area
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            textArea.read(reader, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        JPanel panelDuongDan = new JPanel();
        panelDuongDan.add(new JLabel("Đường dẫn:"));
        panelDuongDan.add(txtDuongDan);
        panelDuongDan.add(btnLuu);

        add(scrollPane, BorderLayout.CENTER);
        add(panelDuongDan, BorderLayout.SOUTH);

        setVisible(true);
    }
}
