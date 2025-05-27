package BackEnd_Hai.newpackage;

// CREATE TABLE books (
//     MaSach SERIAL PRIMARY KEY,
//     MaNXB INT,
//     UserID INT,
//     TenSach VARCHAR(50),
//     NamXuatBan TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//     Gia INT,
//     MoTa VARCHAR(1000),
//     FOREIGN KEY (MaNXB) REFERENCES publisher(MaNXB)
// );

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class Books {

    public String addBook(int MaNXB, int UserID, String TenSach, int Gia, String MoTa) {
        String sql = "INSERT INTO books (MaNXB, UserID, TenSach, Gia, MoTa) VALUES (?, ?, ?, ?, ?) RETURNING *";
        try (
                Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, MaNXB);
            stmt.setInt(2, UserID);
            stmt.setString(3, TenSach);
            stmt.setInt(4, Gia);
            stmt.setString(5, MoTa);

            ResultSet rs = stmt.executeQuery();

            JSONObject json = new JSONObject();
            json.put("MaSach", rs.getInt("MaSach"));
            json.put("MaNXB", rs.getInt("MaNXB"));
            json.put("UserID", rs.getInt("UserID"));
            json.put("TenSach", rs.getString("TenSach"));
            json.put("Gia", rs.getInt("Gia"));
            json.put("MoTa", rs.getString("MoTa"));

            return json.toString();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thêm sách: " + e.getMessage());
        }
    }

    public String BookMiddlewareAdd(String tenNXB, int userID, String tenSach, int gia, String moTa) {
        Publisher publisher = new Publisher();
        int MaNXB = publisher.getMaNXB(tenNXB);

        if (MaNXB == -1) {
            // Nếu chưa tồn tại, thêm NXB mới và lấy lại MaNXB
            String jsonStr = publisher.addPublisher(tenNXB, "Địa chỉ mặc định");
            JSONObject json = new JSONObject(jsonStr);
            MaNXB = json.getInt("MaNXB");
        }

        // Thêm sách với MaNXB đã xác định
        String result = addBook(MaNXB, userID, tenSach, gia, moTa);

        return result;
    }

    public String getAllBookCreated(int userID) {
        String sql = "SELECT * FROM books INNER JOIN publisher ON books.MaNXB = publisher.MaNXB WHERE UserID = ?";
        JSONArray booksArray = new JSONArray();

        try (
                Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JSONObject book = new JSONObject();
                book.put("MaSach", rs.getInt("MaSach"));
                book.put("MaNXB", rs.getInt("MaNXB"));
                book.put("UserID", rs.getInt("UserID"));
                book.put("TenSach", rs.getString("TenSach"));
                book.put("NamXuatBan", rs.getTimestamp("NamXuatBan").toString());
                book.put("Gia", rs.getInt("Gia"));
                book.put("MoTa", rs.getString("MoTa"));
                book.put("TenNXB", rs.getString("TenNXB"));

                booksArray.put(book);

                return booksArray.toString(); // Trả về dạng JSON array

            } else {
                throw new RuntimeException("Không tìm thấy sách nào vui lòng tạo: " + userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi truy xuất sách: " + e.getMessage());
        }
    }

    public String getBookDetails(int maSach) {
        String sql = "SELECT * FROM books INNER JOIN publisher ON books.MaNXB = publisher.MaNXB WHERE MaSach = ?";
        try (
                Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maSach);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JSONObject book = new JSONObject();
                book.put("MaSach", rs.getInt("MaSach"));
                book.put("MaNXB", rs.getInt("MaNXB"));
                book.put("UserID", rs.getInt("UserID"));
                book.put("TenSach", rs.getString("TenSach"));
                book.put("NamXuatBan", rs.getTimestamp("NamXuatBan").toString());
                book.put("Gia", rs.getInt("Gia"));
                book.put("MoTa", rs.getString("MoTa"));
                book.put("TenNXB", rs.getString("TenNXB"));

                return book.toString();
            } else {
                throw new RuntimeException("Không tìm thấy sách với mã: " + maSach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi truy xuất thông tin của một sách: " + e.getMessage());
        }
    }
}