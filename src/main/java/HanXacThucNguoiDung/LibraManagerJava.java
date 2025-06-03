package HanXacThucNguoiDung;

import java.util.Scanner;

public class LibraManagerJava {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n====== LIBRA MANAGER ======");
            if (!Login.isLoggedIn()) {
                System.out.println("1. Đăng ký");
                System.out.println("2. Đăng nhập");
                System.out.println("3. Thoát");
                System.out.print("Chọn: ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        Register.registerUser();
                        break;
                    case "2":
                        Login.loginUser();
                        break;
                    case "3":
                        System.out.println("Thoát chương trình.");
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }
            } else {
                System.out.println("Bạn đã đăng nhập. Vui lòng đăng xuất để tiếp tục với tài khoản khác.");
                Login.showLoggedInMenu(); // Gọi menu sau đăng nhập
            }
        }
    }
}