package HanXacThucNguoiDung;

public class Insert {
    public static void main(String[] args) {
        String hoten = "Do Van Hai";
        String ngaySinh = "2005-02-02";
        String phone = "0911223344";
        String email = "hai@dv.com";
        String diachi = "123 Duong ABC";
        String password = "123456";

        try {
            Users.insertUser(hoten, ngaySinh, phone, email, diachi, password);
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm người dùng: " + e.getMessage());
        }
    }
}