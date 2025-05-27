package com.javateam.libramanagerjava;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;

public class Register {
    public static void registerUser() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Nhập họ và tên: ");
        String fullName = scanner.nextLine().trim();

        System.out.print("Nhập ngày sinh (YYYY-MM-DD): ");
        String dateOfBirth = scanner.nextLine().trim();

        System.out.print("Nhập số điện thoại: ");
        String phone = scanner.nextLine().trim();

        System.out.print("Nhập email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Nhập địa chỉ: ");
        String address = scanner.nextLine().trim();

        System.out.print("Nhập mật khẩu: ");
        String password = scanner.nextLine().trim();

        // Kiểm tra định dạng
        if (!isValidEmail(email)) {
            System.out.println("Email không hợp lệ!");
            return;
        }
        if (!isValidPhone(phone)) {
            System.out.println("Số điện thoại không hợp lệ!");
            return;
        }
        if (!isValidDateOfBirth(dateOfBirth)) {
            System.out.println("Ngày sinh không hợp lệ (định dạng YYYY-MM-DD)!");
            return;
        }
        if (fullName.isEmpty() || address.isEmpty() || password.isEmpty()) {
            System.out.println("Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        try {
            List<User> users = UserFileManager.readUsers();

            // Kiểm tra email đã tồn tại
            for (User u : users) {
                if (u.getEmail().equalsIgnoreCase(email)) {
                    System.out.println("Email đã tồn tại!");
                    return;
                }
            }

            // Giả lập gửi mã xác nhận
            System.out.print("Nhập mã xác nhận (giả lập: 123456): ");
            String confirmationCode = scanner.nextLine().trim();
            if (!confirmationCode.equals("123456")) {
                System.out.println("Mã xác nhận không đúng!");
                return;
            }

            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            users.add(new User(fullName, dateOfBirth, phone, email, address, hashed));

            UserFileManager.writeUsers(users);
            System.out.println("Đăng ký thành công!");

        } catch (IOException e) {
            System.out.println("Lỗi khi lưu người dùng: " + e.getMessage());
        }
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailRegex, email);
    }

    private static boolean isValidPhone(String phone) {
        String phoneRegex = "^\\d{10}$";
        return Pattern.matches(phoneRegex, phone);
    }

    private static boolean isValidDateOfBirth(String dob) {
        String dobRegex = "^\\d{4}-\\d{2}-\\d{2}$";
        return Pattern.matches(dobRegex, dob);
    }
}