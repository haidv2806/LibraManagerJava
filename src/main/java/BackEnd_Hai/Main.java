package BackEnd_Hai;

import java.sql.SQLException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            Books books = new Books();
            // Thêm sách mới
            // String result = books.BookMiddlewareAdd(
            //     "NXB IPM",          // tên nhà xuất bản
            //     1,                 // mã người dùng (giả định đã có)
            //     "Đỗ Văn Hải",       // tên tác giả
            //     "Sách mới về Java", // tên sách
            //     100000,             // giá
            //     "Mô tả sách học Java từ cơ bản đến nâng cao",  // mô tả
            //     Arrays.asList(1, 2) // danh sách mã thể loại (giả định MaTL = 1,2 đã có)
            // );
            // System.out.println("✅ Kết quả thêm sách: \n" + result);

            // String result2 = books.getAllBookCreated(1);
            // System.out.println("📚 Các sách đã tạo: \n" + result2);

            String result3 = books.getBookDetails(5);
            System.out.println("🔍 Thông tin chi tiết sách: \n" + result3);

            // String result4 = books.editBook(2, "Sách sau khi sửa", 150000, "Mô tả sách sau khi sửa", 1);
            // System.out.println("kết quả sau khi cập nhậtnhật: " + result4);

            // String result5 = books.deleteBook(2);
            // System.out.println("Kết quả xóa sách: " + result5);

                        // Thêm một volume mới cho sách có MaSach = 1
            Volume volume = new Volume();
            // String addResult = volume.addVolume(3, "Volume 2", "/path/to/volume1.pdf");
            // System.out.println("Kết quả thêm volume: " + addResult);

            // // Xem danh sách volume của sách có MaSach = 1
            // String listResult = volume.getVolumesByBook(3);
            // System.out.println("Danh sách volume của sách 1: " + listResult);

            // // Xem nội dung của volume vừa thêm (giả sử volume_id = 1)
            // String contentResult = volume.getVolumeContent(2);
            // System.out.println("Nội dung volume 1: " + contentResult);

            // Sửa thông tin volume (giả sử volume_id = 1)
            // String editResult = volume.editVolume(1, "Volume 1 đã sửa", "/path/to/volume1_edited.pdf");
            // System.out.println("Kết quả sửa volume: " + editResult);

            // // Xóa volume (giả sử volume_id = 1)
            // String deleteResult = volume.deleteVolume(1);
            // System.out.println("Kết quả xóa volume: " + deleteResult);
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



// CREATE TABLE nguoi_dung(
//     MaND SERIAL PRIMARY KEY,
//     HoTen VARCHAR(20) UNIQUE NOT NULL,
//     Password VARCHAR(255) NOT NULL,
//     phone VARCHAR(20),
//     Email VARCHAR(20),
//     DiaChi VARCHAR(50)
// )