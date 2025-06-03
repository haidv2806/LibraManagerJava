/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaceDung;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author admin
 */
public class Page2 extends JFrame{
    private JFrame parent;
    private List<JPanel> genrePanels = new ArrayList<>();
    private JPanel genresContainer;
    private String[] genreOptions = {"Hài", "Kinh dị", "Lãng mạn", "Khoa học", "Lịch sử"};
    private JTextField nameField, authorField, publishCodeField;
    private String bookId; // Lưu ID sách để xác định chế độ chỉnh sửa

    public Page2(JFrame parent, String bookId) {
        this.parent = parent;
        this.bookId = bookId;
        setTitle(bookId == null ? "Trang 2 - Thêm sách mới" : "Trang 2 - Sửa sách");
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
        
        addFormField(formPanel, "Tên sách:");
        addFormField(formPanel, "Tác giả:");
        
        JPanel genreLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genreLabelPanel.add(new JLabel("Thể loại:"));
        formPanel.add(genreLabelPanel);
        
        genresContainer = new JPanel();
        genresContainer.setLayout(new BoxLayout(genresContainer, BoxLayout.Y_AXIS));
        addGenreField();
        
        JPanel genreButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addGenreButton = new JButton("+ Thêm thể loại");
        addGenreButton.setBackground(Color.GREEN);
        addGenreButton.addActionListener(e -> addGenreField());
        genreButtonPanel.add(addGenreButton);
        
        formPanel.add(genresContainer);
        formPanel.add(genreButtonPanel);
        
        addFormField(formPanel, "Mã xuất bản:");
        
        JButton saveButton = new JButton("Lưu");
        saveButton.setBackground(Color.GREEN);
        saveButton.addActionListener(e -> {
            // Kiểm tra null để tránh NullPointerException
            if (nameField == null || authorField == null || publishCodeField == null) {
                JOptionPane.showMessageDialog(this, "Lỗi: Một hoặc nhiều trường chưa được khởi tạo!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String bookName = nameField.getText().trim();
            String author = authorField.getText().trim();
            String publishCode = publishCodeField.getText().trim();
            String genres = genrePanels.stream()
                .map(panel -> ((JComboBox<?>) panel.getComponent(0)).getSelectedItem().toString())
                .collect(Collectors.joining(", "));
            
            // Kiểm tra các trường bắt buộc
            if (bookName.isEmpty() || author.isEmpty() || publishCode.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Phân biệt giữa thêm mới và chỉnh sửa
            if (bookId == null) {
                // Thêm sách mới
                ((Page1) parent).addBook(publishCode, bookName, author, genres, "28/06/26");
                JOptionPane.showMessageDialog(this, "Đã thêm sách: " + bookName);
            } else {
                // Cập nhật sách hiện có
                ((Page1) parent).updateBook(bookId, bookName, author, genres, "28/06/26");
                JOptionPane.showMessageDialog(this, "Đã cập nhật sách: " + bookName);
            }
            
            parent.setVisible(true);
            dispose();
        });
        
        formPanel.add(saveButton);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(formPanel), BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void addFormField(JPanel panel, String label) {
        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldPanel.add(new JLabel(label));
        JTextField textField = new JTextField(20);
        fieldPanel.add(textField);
        panel.add(fieldPanel);
        
        // In nhãn để kiểm tra
        System.out.println("Adding field with label: '" + label + "'");
        
        // Gán trường dựa trên nhãn, sử dụng trim() để loại bỏ khoảng trắng thừa
        if (label.trim().equals("Tên sách:")) {
            nameField = textField;
        } else if (label.trim().equals("Tác giả:")) {
            authorField = textField;
        } else if (label.trim().equals("Mã xuất bản:")) {
            publishCodeField = textField;
        }
    }
    
    private void addGenreField() {
        JPanel genrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JComboBox<String> genreCombo = new JComboBox<>(genreOptions);
        genreCombo.setPreferredSize(new Dimension(150, 25));
        
        JButton removeButton = new JButton("x");
        removeButton.setBackground(Color.GREEN);
        removeButton.setForeground(Color.WHITE);
        
        if (genrePanels.size() >= 1) {
            removeButton.addActionListener(e -> {
                genresContainer.remove(genrePanel);
                genrePanels.remove(genrePanel);
                genresContainer.revalidate();
                genresContainer.repaint();
            });
        } else {
            removeButton.setEnabled(false);
        }
        
        genrePanel.add(genreCombo);
        genrePanel.add(removeButton);
        
        genresContainer.add(genrePanel);
        genrePanels.add(genrePanel);
        
        genresContainer.revalidate();
        genresContainer.repaint();
    }
    
    public void setBookData(String id, String name, String author, String genre) {
        if (nameField != null) nameField.setText(name);
        if (authorField != null) authorField.setText(author);
        if (publishCodeField != null) publishCodeField.setText(id);
        if (genre != null && !genre.isEmpty()) {
            String[] genres = genre.split(", ");
            for (int i = 0; i < genres.length; i++) {
                if (i > 0) addGenreField();
                JComboBox<String> combo = (JComboBox<String>) genrePanels.get(i).getComponent(0);
                combo.setSelectedItem(genres[i]);
            }
        }
    }
}
