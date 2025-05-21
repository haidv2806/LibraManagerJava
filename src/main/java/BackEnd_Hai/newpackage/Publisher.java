package BackEnd_Hai.newpackage;

// CREATE TABLE publisher (
//     MaNXB SERIAL PRIMARY KEY,
//     TenNXB VARCHAR(50),
//     DiaChi VARCHAR(100)
// );
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Publisher {
    int MaNXB;
    String TenNXB;
    String DiaChi;

    public String addPublisher(int maNXB, String tenNXB, String diaChi) {
        this.MaNXB = maNXB;
        this.TenNXB = tenNXB;
        this.DiaChi = diaChi;

        String sql = "INSERT INTO publisher (MaNXB, TenNXB, DiaChi) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, MaNXB);
            stmt.setString(2, TenNXB);
            stmt.setString(3, DiaChi);
            stmt.executeUpdate();

            return "Thêm nhà xuất bản thành công";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getMaNXB(String tenNXB) {
        String sql = "SELECT * FROM publisher WHERE TenNXB = ?";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenNXB);
            return stmt.executeQuery().getInt("MaNXB");
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
