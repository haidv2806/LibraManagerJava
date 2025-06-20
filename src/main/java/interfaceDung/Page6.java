package interfaceDung;

import org.json.JSONArray;
import org.json.JSONObject;

import BackEnd_Hai.Books;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class Page6 extends JFrame {
    private JPanel genresContainer;
    private JFrame parent;

    public Page6(JFrame parent, int bookId) {
        this.parent = parent;
        setTitle("Chi tiết sách");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        try {
            Books book = new Books();
            String bookDetails = book.getBookDetails(bookId);
            JSONObject bookJson = new JSONObject(bookDetails);

            // Tiêu đề
            JLabel titleLabel = new JLabel(bookJson.getString("TenSach"));
            titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(titleLabel);

            mainPanel.add(Box.createVerticalStrut(10));
            JLabel tacGiaLabel = createLabel("Tác giả: " + bookJson.optString("TenTacGia", ""));
            JLabel nxbLabel = createLabel("Nhà xuất bản: " + bookJson.optString("TenNXB", ""));
            
            int gia = bookJson.optInt("Gia", 0);
            DecimalFormat df = new DecimalFormat("#,###");
            String giaFormatted = df.format(gia) + " đ";
            JLabel giaLabel = createLabel("Giá: " + giaFormatted);


            JLabel tgTaoLabel = createLabel("Thời gian tạo: " + bookJson.optString("thoiGianTao", ""));
            tacGiaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            nxbLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            giaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            tgTaoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(tacGiaLabel);
            mainPanel.add(nxbLabel);
            mainPanel.add(giaLabel);
            mainPanel.add(tgTaoLabel);

            mainPanel.add(Box.createVerticalStrut(10));
            JTextArea moTaArea = new JTextArea(bookJson.optString("MoTa", ""));
            moTaArea.setLineWrap(true);
            moTaArea.setWrapStyleWord(true);
            moTaArea.setEditable(false);
            moTaArea.setBorder(BorderFactory.createTitledBorder("Mô tả"));
            moTaArea.setAlignmentX(Component.LEFT_ALIGNMENT);

            JScrollPane moTaScroll = new JScrollPane(moTaArea);
            moTaScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
            moTaScroll.setPreferredSize(new Dimension(400, 200));
            moTaScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(moTaScroll);

            mainPanel.add(Box.createVerticalStrut(10));
            JLabel theLoaiLabel = new JLabel("Thể loại:");
            theLoaiLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(theLoaiLabel);

            genresContainer = new JPanel();
            genresContainer.setLayout(new BoxLayout(genresContainer, BoxLayout.Y_AXIS));
            genresContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
            loadGenres(bookJson.optJSONArray("TheLoai"));
            mainPanel.add(genresContainer);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        // Nút quay lại
        JButton backButton = new JButton("Quay lại");
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.addActionListener(evt -> {
            this.dispose();
            if (parent != null)
                parent.setVisible(true);
        });
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(backButton);

        setContentPane(mainPanel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    // Hiển thị từng thể loại theo tên
    private void loadGenres(JSONArray genres) {
        if (genres == null)
            return;
        for (int i = 0; i < genres.length(); i++) {
            try {
                JSONObject obj = genres.getJSONObject(i);
                String tenTL = obj.optString("TenTL", "");
                JLabel genreLabel = new JLabel("- " + tenTL);
                genreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                genresContainer.add(genreLabel);
            } catch (Exception ex) {
                // Nếu không phải JSONObject thì bỏ qua
            }
        }
    }
}
