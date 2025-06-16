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
// INSERT INTO the_loai (TenTL, MoTa) VALUES
// ('Hành động', 'Thể loại phim với nhiều cảnh đánh nhau, rượt đuổi và các pha nguy hiểm.'),
// ('Tình cảm', 'Phim xoay quanh các mối quan hệ tình cảm giữa các nhân vật.'),
// ('Kinh dị', 'Thể loại phim nhằm gây sợ hãi, hồi hộp với các yếu tố siêu nhiên hoặc tâm lý.'),
// ('Hài hước', 'Phim có nội dung vui nhộn, gây tiếng cười cho khán giả.'),
// ('Phiêu lưu', 'Phim kể về hành trình khám phá, mạo hiểm ở những vùng đất mới.'),
// ('Khoa học viễn tưởng', 'Phim khai thác các yếu tố công nghệ tương lai, không gian, thời gian, người ngoài hành tinh.'),
// ('Hoạt hình', 'Phim được sản xuất bằng kỹ thuật hoạt họa, thường dành cho mọi lứa tuổi.'),
// ('Chiến tranh', 'Phim lấy bối cảnh các cuộc chiến tranh lịch sử hoặc hiện đại.'),
// ('Tài liệu', 'Phim phản ánh hiện thực, cung cấp thông tin và kiến thức về một lĩnh vực cụ thể.'),
// ('Hình sự', 'Phim tập trung vào các vụ án, quá trình điều tra và phá án của cảnh sát.');


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
