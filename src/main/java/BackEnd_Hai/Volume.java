package BackEnd_Hai;

// CREATE TABLE Volume(
//     MaTap SERIAL PRIMARY KEY,
//     MaSach INT,
//     TenTap VARCHAR(50),
//     ViTri TEXT,
//     thoiGianTao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//     FOREIGN KEY (MaSach) REFERENCES sach(MaSach) ON DELETE CASCADE
// )

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class Volume {

    // Thêm volume mới
    public String addVolume(int maSach, String tenTap, String viTri) {
        String sql = "INSERT INTO Volume (MaSach, TenTap, ViTri) VALUES (?, ?, ?) RETURNING *";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maSach);
            stmt.setString(2, tenTap);
            stmt.setString(3, viTri);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JSONObject volume = new JSONObject();
                volume.put("MaTap", rs.getInt("MaTap"));
                volume.put("MaSach", rs.getInt("MaSach"));
                volume.put("TenTap", rs.getString("TenTap"));
                volume.put("ViTri", rs.getString("ViTri"));
                volume.put("thoiGianTao", rs.getTimestamp("thoiGianTao").toLocalDateTime().toLocalDate().toString());
                return volume.toString();
            } else {
                throw new RuntimeException("Không thể thêm volume mới cho sách với id: " + maSach);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            throw new RuntimeException("Lỗi khi thêm volume: " + e.getMessage());
        }
    }

    // Xem danh sách volume của sách
    public String getVolumesByBook(int maSach) {
        String sql = "SELECT * FROM Volume WHERE MaSach = ?";
        JSONArray volumes = new JSONArray();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maSach);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                JSONObject volume = new JSONObject();
                volume.put("MaTap", rs.getInt("MaTap"));
                volume.put("MaSach", rs.getInt("MaSach"));
                volume.put("TenTap", rs.getString("TenTap"));
                volume.put("ViTri", rs.getString("ViTri"));
                volume.put("thoiGianTao", rs.getTimestamp("thoiGianTao").toLocalDateTime().toLocalDate().toString());
                volumes.put(volume);
            }
            return volumes.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy danh sách volume: " + e.getMessage());
        }
    }

    // Xem nội dung của volume (trả về thông tin volume)
    public String getVolumeContent(int maTap) {
        String sql = "SELECT TenTap, ViTri FROM Volume WHERE MaTap = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maTap);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JSONObject volume = new JSONObject();
                volume.put("TenTap", rs.getString("TenTap"));
                volume.put("ViTri", rs.getString("ViTri"));
                return volume.toString();
            } else {
                throw new RuntimeException("Không thể lấy thông tin của volume với id: " + maTap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy nội dung volume: " + e.getMessage());
        }
    }

    // Sửa thông tin volume
    public String editVolume(int maTap, String tenTap, String viTri) {
        String sql = "UPDATE Volume SET TenTap = ?, ViTri = ? WHERE MaTap = ? RETURNING *";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenTap);
            stmt.setString(2, viTri);
            stmt.setInt(3, maTap);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JSONObject volume = new JSONObject();
                volume.put("MaTap", rs.getInt("MaTap"));
                volume.put("MaSach", rs.getInt("MaSach"));
                volume.put("TenTap", rs.getString("TenTap"));
                volume.put("ViTri", rs.getString("ViTri"));
                volume.put("thoiGianTao", rs.getTimestamp("thoiGianTao").toLocalDateTime().toLocalDate().toString());
                return volume.toString();
            } else {
                throw new RuntimeException("Không tìm thấy volume với id: " + maTap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi sửa volume: " + e.getMessage());
        }
    }

    // Xóa volume
    public String deleteVolume(int maTap) {
        String sql = "DELETE FROM Volume WHERE MaTap = ? RETURNING *";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maTap);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "Đã xóa volume với id: " + maTap;
            } else {
                throw new RuntimeException("Không tìm thấy volume với id: " + maTap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi xóa volume: " + e.getMessage());
        }
    }
}
