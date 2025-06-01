package BackEnd_Hai.newpackage;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Books books = new Books();
            // String result = books.BookMiddlewareAdd("IPM", 1, "DoVanHai", "Sách mới", 100000, "Mô tả sách mới");
            // System.out.println("Kết quả thêm sách: " + result);

            // String result2 = books.getAllBookCreated(1);
            // System.out.println("các sách đã tạo: " + result2);

            // String result3 = books.getBookDetails(1);
            // System.out.println("thông tin chi tiết sách: " + result3);

            // String result4 = books.editBook(1, "Sách sau khi sửa", 150000, "Mô tả sách sau khi sửa", 1);
            // System.out.println("kết quả sau khi cập nhậtnhật: " + result4);

            // String result5 = books.deleteBook(1);
            // System.out.println("Kết quả xóa sách: " + result5);

                        // Thêm một volume mới cho sách có MaSach = 1
            Volume volume = new Volume();
            // String addResult = volume.addVolume(2, "Volume 2", "/path/to/volume1.pdf");
            // System.out.println("Kết quả thêm volume: " + addResult);

            // // Xem danh sách volume của sách có MaSach = 1
            // String listResult = volume.getVolumesByBook(2);
            // System.out.println("Danh sách volume của sách 1: " + listResult);

            // // Xem nội dung của volume vừa thêm (giả sử volume_id = 1)
            String contentResult = volume.getVolumeContent(2);
            // System.out.println("Nội dung volume 1: " + contentResult);

            // Sửa thông tin volume (giả sử volume_id = 1)
            // String editResult = volume.editVolume(2, "Volume 1 đã sửa", "/path/to/volume1_edited.pdf");
            // System.out.println("Kết quả sửa volume: " + editResult);

            // // Xóa volume (giả sử volume_id = 1)
            String deleteResult = volume.deleteVolume(2);
            System.out.println("Kết quả xóa volume: " + deleteResult);
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
// sửa thông tin volume
// xóa volume

// CREATE TABLE Volume(
//     volume_id SERIAL PRIMARY KEY,
//     MaSach INT,
//     TenVolume VARCHAR(50),
//     file_path TEXT,
//     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//     FOREIGN KEY (MaSach) REFERENCES books(MaSach) ON DELETE CASCADE
// )



// CREATE TABLE Users(
//     UserID SERIAL PRIMARY KEY,
//     HoTen VARCHAR(20) UNIQUE NOT NULL,
//     Password VARCHAR(255) NOT NULL,
//     phone VARCHAR(20),
//     Email VARCHAR(20),
//     DiaChi VARCHAR(50)
// )