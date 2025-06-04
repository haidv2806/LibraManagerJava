package BackEnd_Hai;

// CREATE TABLE nha_xuat_ban (
//     MaNXB SERIAL PRIMARY KEY,
//     TenNXB VARCHAR(50),
//     DiaChi VARCHAR(100)
// );

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONObject;

public class Publisher {

    public String addPublisher(String tenNXB, String diaChi) {
        String sql = "INSERT INTO nha_xuat_ban (TenNXB, DiaChi) VALUES (?, ?) RETURNING *";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenNXB);
            stmt.setString(2, diaChi);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JSONObject json = new JSONObject();
                json.put("MaNXB", rs.getInt("MaNXB"));
                json.put("TenNXB", rs.getString("TenNXB"));
                json.put("DiaChi", rs.getString("DiaChi"));

                return json.toString(); // Trả về chuỗi JSON
            } else {
                throw new RuntimeException("Không thể thêm nhà xuất bản");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thêm nhà xuất bản: " + e.getMessage());
        }
    }

    public int getMaNXB(String tenNXB) {
        String sql = "SELECT MaNXB FROM nha_xuat_ban WHERE TenNXB = ?";
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenNXB);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("MaNXB");
            } else {
                return -1; // Not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy mã nhà xuất bản: " + e.getMessage());
        }
    }

}
