/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaceDung;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author admin
 */
public class Page1 extends JFrame{
   private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;
    private List<Object[]> allData;

    public Page1() {
        setTitle("Trang 1 - Quản lý sách");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Tạo panel chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new BorderLayout());
        
        JPanel searchInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchInputPanel.add(new JLabel("Tìm kiếm:"));
        searchField = new JTextField(20);
        searchInputPanel.add(searchField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Quay lại");
        backButton.setBackground(Color.GREEN);
        JButton addButton = new JButton("Thêm sách");
        addButton.setBackground(Color.GREEN);
        
        buttonPanel.add(backButton);
        buttonPanel.add(addButton);
        
        searchPanel.add(searchInputPanel, BorderLayout.WEST);
        searchPanel.add(buttonPanel, BorderLayout.EAST);
        
        // Khởi tạo danh sách dữ liệu
        allData = new ArrayList<>();
        allData.add(new Object[]{"1", "Sách 1", "Tác giả 1", "Hải", "28/06/26", ""});
        allData.add(new Object[]{"2", "Sách 2", "Tác giả 1", "Hải", "28/06/26", ""});
        allData.add(new Object[]{"3", "Sách 3", "Tác giả 1", "Hải", "28/06/26", ""});
        allData.add(new Object[]{"4", "Sách 4", "Tác giả 1", "Hải", "28/06/26", ""});
        allData.add(new Object[]{"5", "Sách 5", "Tác giả 1", "Hải", "28/06/26", ""});
        allData.add(new Object[]{"6", "Sách 6", "Tác giả 1", "Hải", "28/06/26", ""});
        
        // Tạo bảng danh sách sách
        String[] columnNames = {"Mã sách", "Tên sách", "Tác giả", "Thể loại", "Ngày tạo", "Thao tác"};
        model = new DefaultTableModel(new Object[][]{}, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };
        
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(150);
        
        table.getColumnModel().getColumn(0).setCellRenderer(new BlueColumnRenderer());
        table.getColumnModel().getColumn(2).setCellRenderer(new BlueColumnRenderer());
        table.getColumnModel().getColumn(4).setCellRenderer(new BlueColumnRenderer());
        
        // Xử lý sự kiện Sửa/Xóa
        table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()) {
            @Override
            protected void deleteRow(int row) {
                int confirm = JOptionPane.showConfirmDialog(null, 
                    "Bạn có chắc muốn xóa sách này?", "Xác nhận xóa", 
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String bookId = (String) model.getValueAt(row, 0);
                    allData.removeIf(data -> data[0].equals(bookId));
                    model.removeRow(row);
                }
            }

            @Override
            protected void editRow(int row) {
                String bookId = (String) model.getValueAt(row, 0);
                String bookName = (String) model.getValueAt(row, 1);
                String author = (String) model.getValueAt(row, 2);
                String genre = (String) model.getValueAt(row, 3);
                String date = (String) model.getValueAt(row, 4);

                Page2 editPage = new Page2(Page1.this, bookId);
                editPage.setBookData(bookId, bookName, author, genre);
                editPage.setVisible(true);
                setVisible(false);
            }
        });
        
        // Sự kiện tìm kiếm
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { search(); }
            public void removeUpdate(DocumentEvent e) { search(); }
            public void changedUpdate(DocumentEvent e) { search(); }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Sự kiện nút thêm sách
        addButton.addActionListener(e -> {
            Page2 trang2 = new Page2(Page1.this, null);
            trang2.setVisible(true);
            setVisible(false);
        });
        
        // Xử lý click vào mã sách
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (col == 0) {
                    String bookId = (String) table.getValueAt(row, 0);
                    String bookName = (String) table.getValueAt(row, 1);
                    Page3 trang3 = new Page3(Page1.this, bookId, bookName);
                    trang3.setVisible(true);
                    setVisible(false);
                }
            }
        });
        
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);
        
        // Tải dữ liệu ban đầu
        search();
    }
    
    private void search() {
        String searchText = searchField.getText().toLowerCase();
        model.setRowCount(0);
        
        for (Object[] row : allData) {
            if (searchText.isEmpty() || 
                row[0].toString().toLowerCase().contains(searchText) ||
                row[1].toString().toLowerCase().contains(searchText) ||
                row[2].toString().toLowerCase().contains(searchText) ||
                row[3].toString().toLowerCase().contains(searchText)) {
                model.addRow(row);
            }
        }
    }
    
    public void addBook(String id, String name, String author, String genre, String date) {
        Object[] newRow = {id, name, author, genre, date, ""};
        allData.add(newRow);
        search();
    }
    
    public void updateBook(String id, String name, String author, String genre, String date) {
        for (int i = 0; i < allData.size(); i++) {
            if (allData.get(i)[0].equals(id)) {
                allData.set(i, new Object[]{id, name, author, genre, date, ""});
                break;
            }
        }
        search();
    }
}
