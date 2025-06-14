package interfaceDung;

import BackEnd_Hai.Volume;
import BackEnd_Phuong.FileEditorApp;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Page7 extends JFrame {
    private JTextArea contentArea;

    public Page7(int maTap) {
        setTitle("Đọc truyện - Tập " + maTap);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Nội dung tập truyện", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(contentArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Lấy nội dung tập
        try {
            Volume volume = new Volume();
            String jsonStr = volume.getVolumeContent(maTap);
            JSONObject obj = new JSONObject(jsonStr);
            String tenTap = obj.optString("TenTap", "");
            String viTri = obj.optString("ViTri", "");

            setTitle("Đọc truyện - " + tenTap);

            if (viTri.endsWith(".txt") || viTri.endsWith(".md")) {
                String content = FileEditorApp.readText(new File(viTri));
                contentArea.setText(content);
            } else if (viTri.endsWith(".docx")) {
                String content = FileEditorApp.readDocx(new File(viTri));
                contentArea.setText(content);
            } else {
                contentArea.setText("Không hỗ trợ định dạng file này hoặc đường dẫn không hợp lệ.");
            }
        } catch (Exception e) {
            contentArea.setText("Lỗi khi tải nội dung: " + e.getMessage());
        }

        // Nút đóng
        JButton closeBtn = new JButton("Đóng");
        closeBtn.addActionListener(e -> dispose());
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(closeBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }
}
