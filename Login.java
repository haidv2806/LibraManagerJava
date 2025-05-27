package com.javateam.libramanagerjava;

import java.io.IOException;
import java.util.*;
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
            List<User> users = UserFileManager.readUsers();
            for (User user : users) {
                if (user.getEmail().equalsIgnoreCase(email)) {
                    if (BCrypt.checkpw(password, user.getHashedPassword())) {
                        loggedInUser = user;
                        System.out.println("Đăng nhập thành công!");
                        showLoggedInMenu(); // Gọi menu sau đăng nhập
                        return;
                    } else {
                        System.out.println("Mật khẩu sai. Vui lòng thử lại.");
                        return;
                    }
                }
            }
            System.out.println("Không tìm thấy email. Vui lòng thử lại.");
        } catch (IOException e) {
            System.out.println("Lỗi đọc file: " + e.getMessage());
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

    public static void showLoggedInMenu() {
        Scanner scanner = new Scanner(System.in);
        while (isLoggedIn()) {
            System.out.println("\n====== MENU SAU ĐĂNG NHẬP ======");
            System.out.println("1. Xem thông tin cá nhân");
            System.out.println("2. Đăng xuất");
            System.out.print("Chọn: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("Thông tin cá nhân:");
                    System.out.println("Họ tên: " + loggedInUser.getFullName());
                    System.out.println("Email: " + loggedInUser.getEmail());
                    System.out.println("Ngày sinh: " + loggedInUser.getDateOfBirth());
                    System.out.println("Số điện thoại: " + loggedInUser.getPhone());
                    System.out.println("Địa chỉ: " + loggedInUser.getAddress());
                    break;
                case "2":
                    logout();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}