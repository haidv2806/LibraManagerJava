package HanXacThucNguoiDung;

import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;

public class Login {
    private static User loggedInUser = null;

    public static void loginUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Nhập mật khẩu: ");
        String password = scanner.nextLine().trim();

        try {
            User user = Users.getUserByEmail(email);
            if (user != null) {
                if (BCrypt.checkpw(password, user.getHashedPassword())) {
                    loggedInUser = user;
                    System.out.println("Đăng nhập thành công!");
                    // showLoggedInMenu();
                } else {
                    System.out.println("Mật khẩu sai. Vui lòng thử lại.");
                }
            } else {
                System.out.println("Không tìm thấy email. Vui lòng thử lại.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đăng nhập: " + e.getMessage());
        }
    }
    public static boolean isLoggedIn() {
        return loggedInUser != null;
    }
    public static User getLoggedInUser() {
        return loggedInUser;
    }
    public static void logout() {
        loggedInUser = null;
        System.out.println("Đã đăng xuất.");
    }
}