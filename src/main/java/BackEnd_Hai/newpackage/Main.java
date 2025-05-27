package BackEnd_Hai.newpackage;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Books books = new Books();
            // String result = books.BookMiddlewareAdd("Nhà xuất bản ABC", 1, "Sách của đỗ
            // hải tạo", 100000, "Mô tả sách");
            // System.out.println("Kết quả thêm sách: " + result);

            // String result2 = books.getAllBookCreated(1);
            // System.out.println("các sách đã tạo: " + result2);

            // String result3 = books.getBookDetails(1);
            // System.out.println("thông tin chi tiết sách: " + result3);

            // String result4 = books.editBook(1, "Sách sau khi sửa", 150000, "Mô tả sách sau khi sửa", 1);
            // System.out.println("kết quả sau khi cập nhậtnhật: " + result4);

            String result5 = books.deleteBook(1);
            System.out.println("Kết quả xóa sách: " + result5);
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
// chcp 65001
// thêm sách {xong}
// xem danh sách sách {xong}
// xem chi tiết sách {xong} 
// sửa thông tin sách {xong}
// xóa sách {xong}
// tìm kiếm sách {xong} {Dung}
// thêm volume vào sách
// xem danh sách volume của sách
// xem nội dung của volume
