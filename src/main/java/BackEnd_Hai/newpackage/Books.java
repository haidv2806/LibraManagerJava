package BackEnd_Hai.newpackage;

// CREATE TABLE books (
//     MaSach SERIAL PRIMARY KEY,
//     MaNXB INT,
//     UserID INT,
//     TenSach VARCHAR(50),
//     NamXuatBan TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//     Gia INT,
//     MoTa VARCHAR(1000),
//     FOREIGN KEY (MaNXB) REFERENCES publisher(MaNXB)
// );

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONObject;

public class Books {

    public String addBook(int MaNXB, int UserID, String TenSach, int Gia, String MoTa) {
        String sql = "INSERT INTO books (MaNXB, UserID, TenSach, Gia, MoTa) VALUES (?, ?, ?, ?, ?) RETURNING *";
        try (
                Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, MaNXB);
            stmt.setInt(2, UserID);
            stmt.setString(3, TenSach);
            stmt.setInt(4, Gia);
            stmt.setString(5, MoTa);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JSONObject json = new JSONObject();
                json.put("MaSach", rs.getInt("MaSach"));
                json.put("MaNXB", rs.getInt("MaNXB"));
                json.put("UserID", rs.getInt("UserID"));
                json.put("TenSach", rs.getString("TenSach"));
                json.put("Gia", rs.getInt("Gia"));
                json.put("MoTa", rs.getString("MoTa"));

                return json.toString();
            } else {
                throw new RuntimeException("Không có bản ghi nào được trả về sau khi thêm sách.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thêm sách: " + e.getMessage());
        }
    }

    public String BookMiddlewareAdd(String tenNXB, int userID, String tenSach, int gia, String moTa) {
        Publisher publisher = new Publisher();
        int MaNXB = publisher.getMaNXB(tenNXB);

        if (MaNXB == -1) {
            // Nếu chưa tồn tại, thêm NXB mới và lấy lại MaNXB
            String jsonStr = publisher.addPublisher(tenNXB, "Địa chỉ mặc định");
            JSONObject json = new JSONObject(jsonStr);
            MaNXB = json.getInt("MaNXB");
        }

        // Thêm sách với MaNXB đã xác định
        String result = addBook(MaNXB, userID, tenSach, gia, moTa);

        return result;
    }
}