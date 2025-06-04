package BackEnd_Hai.newpackage;

// CREATE TABLE theloai_sach(
//     MaTL INT,
//     MaSach INT,
//     PRIMARY KEY (MaTL, MaSach),
//     FOREIGN KEY (MaTL) REFERENCES the_loai(MaTL) ON DELETE CASCADE,
//     FOREIGN KEY (MaSach) REFERENCES sach(MaSach) ON DELETE CASCADE
// )

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class Book_Cathegories {

    public String addBookCathegory(int maTL, int maSach) {
        String sql = "INSERT INTO theloai_sach (MaTL, MaSach) VALUES (?, ?)";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maTL);
            stmt.setInt(2, maSach);
            stmt.executeUpdate();

            return "Thêm thể loại sách thành công";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getCategoriesByBookId(int maSach) {
        JSONArray dsTheLoai = new JSONArray();
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT the_loai.MaTL, the_loai.TenTL FROM theloai_sach " +
                    "INNER JOIN the_loai ON theloai_sach.MaTL = the_loai.MaTL " +
                    "WHERE theloai_sach.MaSach = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maSach);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                JSONObject theLoai = new JSONObject();
                theLoai.put("MaTL", rs.getInt("MaTL"));
                theLoai.put("TenTL", rs.getString("TenTL"));
                dsTheLoai.put(theLoai);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsTheLoai;
    }
}
