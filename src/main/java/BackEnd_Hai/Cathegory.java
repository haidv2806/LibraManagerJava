package BackEnd_Hai;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.ResultSet;

// CREATE TABLE the_loai (
//     MaTL SERIAL PRIMARY KEY,
//     TenTL VARCHAR(50),
//     MoTa VARCHAR(1000)
// );

public class Cathegory {
    // private int MaTL;
    // private String TenTL;
    // private String MoTa;

    public String addCathegory(int maTL, String tenTL, String moTa) {
        // this.MaTL = maTL;
        // this.TenTL = tenTL;
        // this.MoTa = moTa;

        String sql = "INSERT INTO the_loai (MaTL, TenTL, MoTa) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maTL);
            stmt.setString(2, tenTL);
            stmt.setString(3, moTa);
            stmt.executeUpdate();

            return "Thêm thành công";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTenTL(int MaTL) {
        String sql = "SELECT TenTL FROM the_loai WHERE MaTL = ?";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, MaTL);
            return stmt.executeQuery().getString("TenTL");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getMultiTenTL(int[] MaTL) {
        if (MaTL.length == 0) {
            return null;
        }

        StringBuilder sql = new StringBuilder("SELECT TenTL FROM the_loai WHERE MaTL IN (");
        for (int i = 0; i < MaTL.length; i++) {
            sql.append("?");
            if (i < MaTL.length - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < MaTL.length; i++) {
                stmt.setInt(i + 1, MaTL[i]);
            }
            return stmt.executeQuery().getString("TenTL");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getAllCategories() {
        String sql = "SELECT MaTL, TenTL FROM the_loai";
        JSONArray result = new JSONArray();

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("maTL", rs.getInt("MaTL"));
                obj.put("tenTL", rs.getString("TenTL"));
                result.put(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}
