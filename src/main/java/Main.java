import javax.swing.SwingUtilities;

import HanXacThucNguoiDung.*;
import interfaceDung.Page1;

public class Main {
        public static void main(String[] args) {
        // Hiển thị trang 1 khi chương trình khởi chạy
        SwingUtilities.invokeLater(() -> {
            MainForm MainForm = new MainForm();
            MainForm.setVisible(true);
        });
    }
}
