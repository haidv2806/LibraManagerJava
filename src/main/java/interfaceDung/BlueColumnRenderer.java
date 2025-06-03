package interfaceDung;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.Component;
/**
 *
 * @author admin
 */
public class BlueColumnRenderer extends DefaultTableCellRenderer{
     public BlueColumnRenderer() {
        setOpaque(true);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, 
                isSelected, hasFocus, row, column);
        c.setBackground(new Color(52, 152, 219)); // Màu xanh dương
        c.setForeground(Color.WHITE);
        return c;
    }
}
