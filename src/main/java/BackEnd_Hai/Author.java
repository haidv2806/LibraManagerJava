package BackEnd_Hai;

// CREATE TABLE tac_gia (
//     MaTG SERIAL PRIMARY KEY,
//     TenTG VARCHAR(50),
//     SoDT VARCHAR(20)
// );

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONObject;

public class Author {

    protected String addAuthor(String tenTG, String soDT) {
        String sql = "INSERT INTO tac_gia (TenTG, SoDT) VALUES (?, ?) RETURNING *";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenTG);
            stmt.setString(2, soDT);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JSONObject json = new JSONObject();
                json.put("MaTG", rs.getInt("MaTG"));
                json.put("TenNXB", rs.getString("TenTG"));
                json.put("DiaChi", rs.getString("SoDT"));

                return json.toString(); // Trả về chuỗi JSON
            } else {
                throw new RuntimeException("Không thể thêm tác giả: " + tenTG);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected int getMaTG(String tenTG) {
        String sql = "SELECT * FROM tac_gia WHERE TenTG = ?";

        try (
                Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenTG);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("MaTG");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
