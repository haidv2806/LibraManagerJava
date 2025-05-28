package BackEnd_Hai.newpackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Users {

    public static void insertUser(String hoTen, String password, String phone, String email, String diaChi) {
        String sql = "INSERT INTO Users (HoTen, Password, phone, Email, DiaChi) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hoTen);
            stmt.setString(2, password);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.setString(5, diaChi);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("✅ Thêm người dùng thành công.");
            } else {
                System.out.println("❌ Không thêm được người dùng.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi thêm người dùng: " + e.getMessage());
        }
    }
    
}
