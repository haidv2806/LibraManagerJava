package HanXacThucNguoiDung;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;

// CREATE TABLE IF NOT EXISTS nguoi_dung (
//     MaND SERIAL PRIMARY KEY,
//     hoten VARCHAR(20) NOT NULL,
//     ngaySinh VARCHAR(10) NOT NULL,
//     SoDT VARCHAR(20) NOT NULL,
//     email VARCHAR(20) NOT NULL UNIQUE,
//     diachi VARCHAR(50) NOT NULL,
//     matkhau VARCHAR(60) NOT NULL
// );

public class Users {
    
    public static void insertUser(String hoten, String ngaySinh, String phone, String email, String diachi, String password) throws SQLException{
        String sql = "INSERT INTO nguoi_dung (hoten, ngaySinh, SoDT, email, diachi, matkhau) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hoten.length() > 20 ? hoten.substring(0, 20) : hoten);
            stmt.setString(2, ngaySinh);
            stmt.setString(3, phone.length() > 20 ? phone.substring(0, 20) : phone);
            stmt.setString(4, email.length() > 20 ? email.substring(0, 20) : email);
            stmt.setString(5, diachi.length() > 50 ? diachi.substring(0, 50) : diachi);
            stmt.setString(6, BCrypt.hashpw(password, BCrypt.gensalt()));
            stmt.executeUpdate();
            System.out.println("Đăng ký thành công!");
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // UNIQUE constraint violation
                System.out.println("Họ tên hoặc email đã tồn tại!");
                throw new SQLException("Họ tên hoặc email đã tồn tại", e);
            } else {
                System.out.println("Lỗi khi thêm người dùng: " + e.getMessage());
                throw new SQLException("Họ tên hoặc email đã tồn tại", e);
            }
        }
    }

    public static User getUserByEmail(String email) {
        String sql = "SELECT * FROM nguoi_dung WHERE email = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("MaND"),
                    rs.getString("hoten"),
                    rs.getString("ngaySinh"),
                    rs.getString("SoDT"),
                    rs.getString("email"),
                    rs.getString("diachi"),
                    rs.getString("matkhau")
                );
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm người dùng: " + e.getMessage());
        }
        return null;
    }
}