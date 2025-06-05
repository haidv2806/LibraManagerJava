/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaceDung;
import javax.swing.*;

/**
 *
 * @author admin
 */
public class Main {
    public static void main(String[] args) {
        // Hiển thị trang 1 khi chương trình khởi chạy
        SwingUtilities.invokeLater(() -> {
            Page1 trang1 = new Page1(1);
            trang1.setVisible(true);
        });
    }
}
