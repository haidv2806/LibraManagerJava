package BackEnd_Hai.newpackage;

// CREATE TABLE book_cathegories(
//     MaTL INT,
//     MaSach INT,
//     PRIMARY KEY (MaTL, MaSach),
//     FOREIGN KEY (MaTL) REFERENCES Cathegory(MaTL) ON DELETE CASCADE,
//     FOREIGN KEY (MaSach) REFERENCES Books(MaSach) ON DELETE CASCADE
// )

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Book_Cathegories {
    private int MaTL;
    private int MaSach;

    public String addBookCathegory(int maTL, int maSach) {
        this.MaTL = maTL;
        this.MaSach = maSach;

        String sql = "INSERT INTO book_cathegories (MaTL, MaSach) VALUES (?, ?)";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, MaTL);
            stmt.setInt(2, MaSach);
            stmt.executeUpdate();

            return "Thêm thể loại sách thành công";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
