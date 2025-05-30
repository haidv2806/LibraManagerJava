package com.javateam.libramanagerjava;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Register {
    public static void registerUser() {
        Scanner scanner = new Scanner(System.in);
        String hoten, ngaySinh, phone, email, diachi, password;

        while (true) {
            System.out.print("Nhập họ và tên (tối đa 20 ký tự): ");
            hoten = scanner.nextLine().trim();
            if (!hoten.isEmpty() && hoten.length() <= 20) break;
            System.out.println("Họ tên không hợp lệ! Nhập lại:");
        }

        while (true) {
            System.out.print("Nhập ngày sinh (YYYY-MM-DD): ");
            ngaySinh = scanner.nextLine().trim();
            if (isValidDateOfBirth(ngaySinh)) break;
            System.out.println("Ngày sinh không hợp lệ (định dạng YYYY-MM-DD)! Nhập lại:");
        }

        while (true) {
            System.out.print("Nhập số điện thoại (tối đa 20 ký tự): ");
            phone = scanner.nextLine().trim();
            if (isValidPhone(phone)) break;
            System.out.println("Số điện thoại không hợp lệ! Nhập lại:");
        }

        while (true) {
            System.out.print("Nhập email (tối đa 20 ký tự): ");
            email = scanner.nextLine().trim();
            if (isValidEmail(email) && email.length() <= 20) break;
            System.out.println("Email không hợp lệ hoặc quá dài! Nhập lại:");
        }

        while (true) {
            System.out.print("Nhập địa chỉ (tối đa 50 ký tự): ");
            diachi = scanner.nextLine().trim();
            if (!diachi.isEmpty() && diachi.length() <= 50) break;
            System.out.println("Địa chỉ không hợp lệ! Nhập lại:");
        }

        while (true) {
            System.out.print("Nhập mật khẩu: ");
            password = scanner.nextLine().trim();
            if (!password.isEmpty()) break;
            System.out.println("Mật khẩu không được để trống! Nhập lại:");
        }

        try {
            if (Users.getUserByEmail(email) != null) {
                System.out.println("Email đã tồn tại!");
                return;
            }
            System.out.print("Nhập mã xác nhận (giả lập: 123456): ");
            String confirmationCode = scanner.nextLine().trim();
            if (!confirmationCode.equals("123456")) {
                System.out.println("Mã xác nhận không đúng!");
                return;
            }
            Users.insertUser(hoten, ngaySinh, phone, email, diachi, password);
        } catch (Exception e) {
            System.out.println("Lỗi khi đăng ký: " + e.getMessage());
        }
    }

    private static boolean isValidEmail(String email) {
        return Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email);
    }

    private static boolean isValidPhone(String phone) {
        return Pattern.matches("^\\d{10,20}$", phone);
    }

    private static boolean isValidDateOfBirth(String dob) {
        if (!Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", dob)) return false;
        try {
            LocalDate.parse(dob);
            return dob.length() <= 20;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}