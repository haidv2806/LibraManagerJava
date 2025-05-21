package BackEnd_Hai.newpackage;

// CREATE TABLE books_authors (
//     MaTG INT,
//     MaSach INT,
//     PRIMARY KEY (MaTG, MaSach),
//     FOREIGN KEY (MaTG) REFERENCES author(MaTG),
//     FOREIGN KEY (MaSach) REFERENCES books(MaSach)
// )

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Books_Authors {
    int MaTG;
    int MaSach;

    public String addBooksAuthors(int maTG, int maSach) {
        this.MaTG = maTG;
        this.MaSach = maSach;

        String sql = "INSERT INTO books_authors (MaTG, MaSach) VALUES (?, ?)";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, MaTG);
            stmt.setInt(2, MaSach);
            stmt.executeUpdate();

            return "Thêm sách tác giả thành công";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String MiddlewareAddBooksAuthors(String tenTG, int  maSach){
        int MaTG = new Author().getMaTG(tenTG);
        if (MaTG != -1){
            return addBooksAuthors(MaTG, maSach);
        } else {
            String maTG = new Author().addAuthor(0, tenTG, null);
            if (maTG != null){
                return addBooksAuthors(Integer.parseInt(maTG), maSach);
            } else {
                return "Thêm sách tác giả thất bại";
            }
        }
    }

}
