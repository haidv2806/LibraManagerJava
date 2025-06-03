/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaceDung;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.DefaultCellEditor;

/**
 *
 * @author admin
 */
public abstract class ButtonEditor extends DefaultCellEditor{
     protected JPanel panel;
    protected JButton editButton;
    protected JButton deleteButton;
    protected int row;
    protected JTable table;
    
    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        
        editButton.setBackground(Color.GREEN);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        
        editButton.setPreferredSize(new Dimension(60, 25));
        deleteButton.setPreferredSize(new Dimension(60, 25));
        
        editButton.addActionListener(e -> {
            fireEditingStopped();
            editRow(row);
        });
        
        deleteButton.addActionListener(e -> {
            fireEditingStopped();
            deleteRow(row);
        });
        
        panel.add(editButton);
        panel.add(deleteButton);
    }
    
    protected abstract void deleteRow(int row);
    protected abstract void editRow(int row);
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, 
            boolean isSelected, int row, int column) {
        this.row = row;
        this.table = table;
        return panel;
    }
    
    @Override
    public Object getCellEditorValue() {
        return "";
    }
}
