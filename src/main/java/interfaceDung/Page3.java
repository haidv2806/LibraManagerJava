/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaceDung;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import BackEnd_Hai.*;

/**
 *
 * @author admin
 */
public class Page3 extends JFrame {
    private JFrame parent;
    private DefaultTableModel model;
    private JTable table;

    public Page3(JFrame parent, String bookId, String bookName, int maND) {
        this.parent = parent;
        setTitle("Trang 3 - Danh sách tập sách");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel trên cùng với nút đóng và tiêu đề
        JPanel topPanel = new JPanel(new BorderLayout());

        // Nút đóng (X màu đỏ)
        JButton closeButton = new JButton("X");
        closeButton.setForeground(Color.RED);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.addActionListener(e -> {
            parent.setVisible(true);
            dispose();
        });

        // Nút thêm tập mới
        JButton addVolumeButton = new JButton("Thêm tập mới");
        addVolumeButton.setBackground(Color.GREEN);
        addVolumeButton.addActionListener(e -> {
            Page4 trang4 = new Page4(this, bookId);
            trang4.setVisible(true);
            setVisible(false);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);
        buttonPanel.add(addVolumeButton);

        topPanel.add(buttonPanel, BorderLayout.EAST);

        JButton refreshButton = new JButton("Làm mới");
        refreshButton.setBackground(Color.ORANGE);
        refreshButton.addActionListener(e -> {
            refreshData(bookId);
        });
        buttonPanel.add(refreshButton);

        // Tiêu đề sách
        JLabel titleLabel = new JLabel("Sách: " + bookName, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // Bảng danh sách tập sách
        String[] columnNames = { "Mã tập", "Tên tập", "Ngày tạo", "Thao tác" };
        // Object[][] data = {
        // {"1", "Tập 1 của " + bookName, "28/06/26", ""},
        // {"2", "Tập 2 của " + bookName, "28/06/26", ""},
        // {"3", "Tập 3 của " + bookName, "28/06/26", ""},
        // {"4", "Tập 4 của " + bookName, "28/06/26", ""},
        // {"5", "Tập 5 của " + bookName, "28/06/26", ""},
        // {"6", "Tập 6 của " + bookName, "28/06/26", ""}
        // };

        // model = new DefaultTableModel(data, columnNames) {
        // @Override
        // public boolean isCellEditable(int row, int column) {
        // return column == 3;
        // }
        // };

        ArrayList<Object[]> dataList = new ArrayList<>();
        try {
            String volumes = new Volume().getVolumesByBook(Integer.parseInt(bookId));
            JSONArray arr = new JSONArray(volumes);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);

                Object[] row = {
                        obj.get("MaTap").toString(),
                        obj.getString("TenTap"),
                        obj.optString("thoiGianTao", ""),
                        "" // Cột thao tác
                };
                dataList.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu tập sách: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }

        Object[][] data = dataList.toArray(new Object[0][]);

        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);

        table.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int column = table.columnAtPoint(evt.getPoint());

                // Nếu cột được click là cột "Tên tập"
                if (column == 1 && row >= 0) {
                    String volumeId = (String) model.getValueAt(row, 0);
                    String volumeName = (String) model.getValueAt(row, 1);

                    // Mở trang chi tiết tập
                    Page7 page = new Page7(Integer.parseInt(volumeId));
                    // page.setVolumeData(volumeName);
                    page.setVisible(true);
                    setVisible(false);
                }
            }
        });

        table.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox()) {
            @Override
            protected void deleteRow(int row) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc muốn xóa tập này?", "Xác nhận xóa",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String volumeId = (String) model.getValueAt(row, 0); // Lấy volumeId từ cột 0
                    Volume volume = new Volume();
                    volume.deleteVolume(Integer.parseInt(volumeId));
                    model.removeRow(row);
                }
            }

            @Override
            protected void editRow(int row) {
                String volumeId = (String) model.getValueAt(row, 0);
                String volumeName = (String) model.getValueAt(row, 1);
                String date = (String) model.getValueAt(row, 2);

                Page8 editPage = new Page8(Page3.this, volumeId, volumeName);
                editPage.setVolumeData(volumeName);
                editPage.setVisible(true);
                setVisible(false);
            }
        });

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(mainPanel);
    }

    public void addVolume(String volumeName, String date) {
        model.addRow(new Object[] { String.valueOf(model.getRowCount() + 1), volumeName, date, "" });
    }

    public void updateVolume(String volumeId, String volumeName, String date) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(volumeId)) {
                model.setValueAt(volumeName, i, 1);
                model.setValueAt(date, i, 2);
                break;
            }
        }
    }

    public void refreshData(String bookId) {
        try {
            String volumes = new Volume().getVolumesByBook(Integer.parseInt(bookId));
            JSONArray arr = new JSONArray(volumes);

            // Xóa dữ liệu cũ
            model.setRowCount(0);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Object[] row = {
                        obj.get("MaTap").toString(),
                        obj.getString("TenTap"),
                        obj.optString("thoiGianTao", ""),
                        ""
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi làm mới dữ liệu: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
