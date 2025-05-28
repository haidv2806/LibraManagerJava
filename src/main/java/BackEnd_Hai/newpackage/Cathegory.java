package BackEnd_Hai.newpackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// CREATE TABLE cathegory (
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

        String sql = "INSERT INTO cathegory (MaTL, TenTL, MoTa) VALUES (?, ?, ?)";

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
        String sql = "SELECT TenTL FROM cathegory WHERE MaTL = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, MaTL);
            return stmt.executeQuery().getString("TenTL");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getMultiTenTL(int[] MaTL){
        if (MaTL.length == 0) {
            return null;
        }

        StringBuilder sql = new StringBuilder("SELECT TenTL FROM cathegory WHERE MaTL IN (");
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
}
