/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaceDung;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
/**
 *
 * @author admin
 */
public class ButtonRenderer extends DefaultTableCellRenderer{
    private JPanel panel;
    private JButton editButton;
    private JButton deleteButton;
    
    public ButtonRenderer() {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        
        editButton.setBackground(Color.GREEN);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        
        editButton.setPreferredSize(new Dimension(60, 25));
        deleteButton.setPreferredSize(new Dimension(60, 25));
        
        panel.add(editButton);
        panel.add(deleteButton);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        return panel;
    }
}
