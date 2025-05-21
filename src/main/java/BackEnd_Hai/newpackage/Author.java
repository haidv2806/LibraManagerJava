package BackEnd_Hai.newpackage;

// CREATE TABLE author (
//     MaTG SERIAL PRIMARY KEY,
//     TenTG VARCHAR(50),
//     SoDT VARCHAR(20)
// );

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Author {
    private int MaTG;
    private String TenTG;
    private String SoDT;

    public String addAuthor(int maTG, String tenTG, String soDT) {
        this.MaTG = maTG;
        this.TenTG = tenTG;
        this.SoDT = soDT;

        String sql = "INSERT INTO author (MaTG, TenTG, SoDT) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, MaTG);
            stmt.setString(2, TenTG);
            stmt.setString(3, SoDT);
            stmt.executeUpdate();

            return "Thêm tác giả thành công";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public String getTenTG(int MaTG) {
        String sql = "SELECT TenTG FROM author WHERE MaTG = ?";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, MaTG);
            return stmt.executeQuery().getString("TenTG");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
