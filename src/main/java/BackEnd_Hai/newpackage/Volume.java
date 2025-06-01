package BackEnd_Hai.newpackage;

// CREATE TABLE Volume(
//     volume_id SERIAL PRIMARY KEY,
//     MaSach INT,
//     TenVolume VARCHAR(50),
//     file_path TEXT,
//     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//     FOREIGN KEY (MaSach) REFERENCES books(MaSach) ON DELETE CASCADE
// )

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class Volume {
    
        public String addVolume(int maSach, String tenVolume, String filePath) {
        String sql = "INSERT INTO Volume (MaSach, TenVolume, file_path) VALUES (?, ?, ?) RETURNING *";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maSach);
            stmt.setString(2, tenVolume);
            stmt.setString(3, filePath);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JSONObject volume = new JSONObject();
                volume.put("volume_id", rs.getInt("volume_id"));
                volume.put("MaSach", rs.getInt("MaSach"));
                volume.put("TenVolume", rs.getString("TenVolume"));
                volume.put("file_path", rs.getString("file_path"));
                volume.put("created_at", rs.getTimestamp("created_at").toString());
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
                volume.put("volume_id", rs.getInt("volume_id"));
                volume.put("MaSach", rs.getInt("MaSach"));
                volume.put("TenVolume", rs.getString("TenVolume"));
                volume.put("file_path", rs.getString("file_path"));
                volume.put("created_at", rs.getTimestamp("created_at").toString());
                volumes.put(volume);
            }
            return volumes.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy danh sách volume: " + e.getMessage());
        }
    }

    // Xem nội dung của volume (giả sử trả về đường dẫn file)
    public String getVolumeContent(int volumeId) {
        String sql = "SELECT TenVolume,  file_path FROM Volume WHERE volume_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, volumeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JSONObject volume = new JSONObject();
                volume.put("TenVolume", rs.getString("TenVolume"));
                volume.put("file_path", rs.getString("file_path"));
                return volume.toString();
            } else {
                throw new RuntimeException("Không thể lấy thông tin của volume với id: " + volumeId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy nội dung volume: " + e.getMessage());
        }
    }

    // Sửa thông tin volume
    public String editVolume(int volumeId, String tenVolume, String filePath) {
        String sql = "UPDATE Volume SET TenVolume = ?, file_path = ? WHERE volume_id = ? RETURNING *";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenVolume);
            stmt.setString(2, filePath);
            stmt.setInt(3, volumeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JSONObject volume = new JSONObject();
                volume.put("volume_id", rs.getInt("volume_id"));
                volume.put("MaSach", rs.getInt("MaSach"));
                volume.put("TenVolume", rs.getString("TenVolume"));
                volume.put("file_path", rs.getString("file_path"));
                volume.put("created_at", rs.getTimestamp("created_at").toString());
                return volume.toString();
            } else {
                throw new RuntimeException("Không tìm thấy volume với id: " + volumeId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi sửa volume: " + e.getMessage());
        }
    }

    // Xóa volume
    public String deleteVolume(int volumeId) {
        String sql = "DELETE FROM Volume WHERE volume_id = ? RETURNING *";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, volumeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "Đã xóa volume với id: " + volumeId;
            } else {
                throw new RuntimeException("Không tìm thấy volume với id: " + volumeId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi xóa volume: " + e.getMessage());
        }
    }
}
