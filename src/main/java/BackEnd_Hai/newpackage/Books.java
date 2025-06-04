package BackEnd_Hai.newpackage;

// CREATE TABLE sach (
//     MaSach SERIAL PRIMARY KEY,
//     MaNXB INT,
//     MaND INT,
//     MaTG INT,
//     TenSach VARCHAR(50),
//     thoiGianTao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//     Gia INT,
//     MoTa VARCHAR(1000),
//     FOREIGN KEY (MaNXB) REFERENCES nha_xuat_ban(MaNXB),
//     FOREIGN KEY (MaND) REFERENCES nguoi_dung(MaND),
//     FOREIGN KEY (MaTG) REFERENCES tac_gia(MaTG)
// );

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class Books {

    // Thêm sách mới
    public String addBook(int MaNXB, int MaND, int MaTG, String TenSach, int Gia, String MoTa) {
        String sql = "INSERT INTO sach (MaNXB, MaND, MaTG, TenSach, Gia, MoTa) VALUES (?, ?, ?, ?, ?, ?) RETURNING *";
        try (
                Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, MaNXB);
            stmt.setInt(2, MaND);
            stmt.setInt(3, MaTG);
            stmt.setString(4, TenSach);
            stmt.setInt(5, Gia);
            stmt.setString(6, MoTa);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JSONObject json = new JSONObject();
                json.put("MaSach", rs.getInt("MaSach"));
                json.put("MaNXB", rs.getInt("MaNXB"));
                json.put("MaND", rs.getInt("MaND"));
                json.put("MaTG", rs.getInt("MaTG"));
                json.put("TenSach", rs.getString("TenSach"));
                json.put("Gia", rs.getInt("Gia"));
                json.put("MoTa", rs.getString("MoTa"));
                json.put("thoiGianTao", rs.getTimestamp("thoiGianTao").toString());
                return json.toString();
            } else {
                throw new RuntimeException("Không tạo được sách: " + TenSach);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thêm sách: " + e.getMessage());
        }
    }

    // Middleware thêm sách (tự động thêm NXB và tác giả nếu chưa có)
    public String BookMiddlewareAdd(String tenNXB, int maND, String tenTacGia, String tenSach, int gia, String moTa) {
        Publisher publisher = new Publisher();
        int MaNXB = publisher.getMaNXB(tenNXB);

        if (MaNXB == -1) {
            // Nếu chưa tồn tại, thêm NXB mới và lấy lại MaNXB
            String jsonStr = publisher.addPublisher(tenNXB, "");
            JSONObject json = new JSONObject(jsonStr);
            MaNXB = json.getInt("MaNXB");
        }

        Author author = new Author();
        int MaTG = author.getMaTG(tenTacGia);
        if (MaTG == -1) {
            // Nếu chưa tồn tại, thêm tác giả mới và lấy lại MaTG
            String jsonStr = author.addAuthor(tenTacGia, "");
            JSONObject json = new JSONObject(jsonStr);
            MaTG = json.getInt("MaTG");
        }

        // Thêm sách với MaNXB và MaTG đã xác định
        String result = addBook(MaNXB, maND, MaTG, tenSach, gia, moTa);

        return result;
    }

    // Lấy tất cả sách đã tạo bởi một user
    public String getAllBookCreated(int maND) {
        String sql = "SELECT sach.*, nha_xuat_ban.TenNXB, tac_gia.TenTG " +
                     "FROM sach " +
                     "INNER JOIN nha_xuat_ban ON sach.MaNXB = nha_xuat_ban.MaNXB " +
                     "INNER JOIN tac_gia ON sach.MaTG = tac_gia.MaTG " +
                     "WHERE sach.MaND = ?";
        JSONArray booksArray = new JSONArray();

        try (
                Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maND);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                JSONObject book = new JSONObject();
                book.put("MaSach", rs.getInt("MaSach"));
                book.put("MaNXB", rs.getInt("MaNXB"));
                book.put("MaND", rs.getInt("MaND"));
                book.put("MaTG", rs.getInt("MaTG"));
                book.put("TenSach", rs.getString("TenSach"));
                book.put("thoiGianTao", rs.getTimestamp("thoiGianTao").toString());
                book.put("Gia", rs.getInt("Gia"));
                book.put("MoTa", rs.getString("MoTa"));
                book.put("TenNXB", rs.getString("TenNXB"));
                book.put("TenTacGia", rs.getString("TenTG"));

                booksArray.put(book);
            }

            if (booksArray.length() == 0) {
                throw new RuntimeException("Không tìm thấy sách nào, vui lòng tạo: " + maND);
            }

            return booksArray.toString(); // Trả về dạng JSON array

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi truy xuất sách: " + e.getMessage());
        }
    }

    // Lấy chi tiết một sách
    public String getBookDetails(int maSach) {
        String sql = "SELECT sach.*, nha_xuat_ban.TenNXB, tac_gia.TenTG " +
                     "FROM sach " +
                     "INNER JOIN nha_xuat_ban ON sach.MaNXB = nha_xuat_ban.MaNXB " +
                     "INNER JOIN tac_gia ON sach.MaTG = tac_gia.MaTG " +
                     "WHERE sach.MaSach = ?";
        try (
                Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maSach);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JSONObject book = new JSONObject();
                book.put("MaSach", rs.getInt("MaSach"));
                book.put("MaNXB", rs.getInt("MaNXB"));
                book.put("MaND", rs.getInt("MaND"));
                book.put("MaTG", rs.getInt("MaTG"));
                book.put("TenSach", rs.getString("TenSach"));
                book.put("thoiGianTao", rs.getTimestamp("thoiGianTao").toString());
                book.put("Gia", rs.getInt("Gia"));
                book.put("MoTa", rs.getString("MoTa"));
                book.put("TenNXB", rs.getString("TenNXB"));
                book.put("TenTacGia", rs.getString("TenTG"));

                return book.toString();
            } else {
                throw new RuntimeException("Không tìm thấy sách với mã: " + maSach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi truy xuất thông tin của một sách: " + e.getMessage());
        }
    }

    // Sửa thông tin sách
    public String editBook(int maSach, String tenSach, int gia, String moTa, int maNXB) {
        String sql = "UPDATE sach SET TenSach = ?, Gia = ?, MoTa = ?, MaNXB = ? WHERE MaSach = ? RETURNING *";
        try (
                Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenSach);
            stmt.setInt(2, gia);
            stmt.setString(3, moTa);
            stmt.setInt(4, maNXB);
            stmt.setInt(5, maSach);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JSONObject book = new JSONObject();
                book.put("MaSach", rs.getInt("MaSach"));
                book.put("TenSach", rs.getString("TenSach"));
                book.put("Gia", rs.getInt("Gia"));
                book.put("MoTa", rs.getString("MoTa"));
                book.put("MaNXB", rs.getInt("MaNXB"));
                return book.toString();
            } else {
                throw new RuntimeException("Không tìm thấy sách với mã: " + maSach);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi cập nhật thông tin của một sách: " + e.getMessage());
        }
    }

    // Xóa sách
    public String deleteBook(int maSach) {
        String sql = "DELETE FROM sach WHERE MaSach = ? RETURNING *";
        try (
                Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maSach);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return "Đã xóa sách với mã: " + maSach;
            } else {
                throw new RuntimeException("Không tìm thấy sách với mã: " + maSach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi xóa sách: " + e.getMessage());
        }
    }
}