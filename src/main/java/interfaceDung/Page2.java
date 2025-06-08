/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaceDung;

import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author admin
 */

import BackEnd_Hai.Books;
import BackEnd_Hai.Cathegory;
import org.json.JSONArray;
import org.json.JSONObject;

public class Page2 extends JFrame {
    private JFrame parent;
    private List<JPanel> genrePanels = new ArrayList<>();
    private JPanel genresContainer;

    private List<JSONObject> genreOptions = new ArrayList<>();

    private JTextField nameField, authorField, publishCodeField;
    private JTextField priceField;
    private JTextArea descriptionArea;
    private String bookId;

    public Page2(JFrame parent, String bookId, int userid) {
        this.parent = parent;
        this.bookId = bookId;
        setTitle("Trang 2 - Thêm sách mới");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // cho thể loại
        JSONArray genresArray = new Cathegory().getAllCategories();
        for (int i = 0; i < genresArray.length(); i++) {
            genreOptions.add(genresArray.getJSONObject(i));
        }

        // Nút đóng
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

        // Form chính
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        addFormField(formPanel, "Tên sách:");
        addFormField(formPanel, "Tác giả:");
        addFormField(formPanel, "Nhà xuất bản:");

        // Thêm trường nhập giá sách
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pricePanel.add(new JLabel("Giá sách:"));
        priceField = new JTextField(10);
        pricePanel.add(priceField);
        formPanel.add(pricePanel);

        // Thêm trường nhập mô tả
        JPanel descPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        descPanel.add(new JLabel("Mô tả:"));
        descriptionArea = new JTextArea(3, 20);
        descPanel.add(new JScrollPane(descriptionArea));
        formPanel.add(descPanel);

        // Thể loại
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

        // Nút lưu
        JButton saveButton = new JButton("Lưu");
        saveButton.setBackground(Color.GREEN);
        saveButton.addActionListener(e -> {
            if (nameField == null || authorField == null || publishCodeField == null) {
                JOptionPane.showMessageDialog(this, "Lỗi: Một hoặc nhiều trường chưa được khởi tạo!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String bookName = nameField.getText().trim();
            String author = authorField.getText().trim();
            String publisherName = publishCodeField.getText().trim();

            if (bookName.isEmpty() || author.isEmpty() || publisherName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Integer> genreIds = mapGenresToIds();
            if (genreIds.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một thể loại!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                

                int price = 0;
                try {
                    price = Integer.parseInt(priceField.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Giá sách phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String description = descriptionArea.getText().trim();
                
                Books middleware = new Books();
                
                String result = middleware.BookMiddlewareAdd(
                        publisherName,
                        userid,
                        author,
                        bookName,
                        price,
                        description,
                        genreIds);

                JOptionPane.showMessageDialog(this, "Đã thêm sách: " + bookName);
                parent.setVisible(true);
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm sách: " + ex.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
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

        if (label.trim().equals("Tên sách:")) {
            nameField = textField;
        } else if (label.trim().equals("Tác giả:")) {
            authorField = textField;
        } else if (label.trim().equals("Nhà xuất bản:")) {
            publishCodeField = textField;
        }
    }

    private void addGenreField() {
        JPanel genrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JComboBox<String> genreCombo = new JComboBox<>();
        for (JSONObject obj : genreOptions) {
            genreCombo.addItem(obj.getString("tenTL"));
        }

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

    private List<Integer> mapGenresToIds() {
        List<Integer> ids = new ArrayList<>();
        for (JPanel panel : genrePanels) {
            JComboBox<?> combo = (JComboBox<?>) panel.getComponent(0);
            String selectedGenre = combo.getSelectedItem().toString();

            for (JSONObject genreObj : genreOptions) {
                if (genreObj.getString("tenTL").equals(selectedGenre)) {
                    ids.add(genreObj.getInt("maTL"));
                    break;
                }
            }
        }
        return ids;
    }
}
