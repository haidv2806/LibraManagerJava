package BackEnd_Hai.newpackage;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Books books = new Books();
            String result = books.BookMiddlewareAdd("Nhà xuất bản ABC", 1, "Sách hay", 100000, "Mô tả sách");
            System.out.println("Kết quả thêm sách: " + result);
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}

// thêm sách {xong}
// xem chi tiết sách
// sửa thông tin sách
// xóa sách
// tìm kiếm sách {xongxong}
// thêm volume vào sách
// xem danh sách volume của sách
// xem nội dung của volume
