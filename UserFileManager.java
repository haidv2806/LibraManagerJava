package com.javateam.libramanagerjava;

import java.io.*;
import java.util.*;

public class UserFileManager {
    private static final String FILE_NAME = "users.txt";

    public static void writeUsers(List<User> list) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));

        // Tiêu đề
        bw.write(String.format("%-20s %-20s %-12s %-30s %-50s %-60s", 
            "FullName", "DateOfBirth", "Phone", "Email", "Address", "HashedPassword"));
        bw.newLine();
        bw.write("-------------------------------------------------------------------------------------------");
        bw.newLine();

        for (User user : list) {
            bw.write(String.format("%-20s %-20s %-12s %-30s %-50s %-60s",
                user.getFullName(), user.getDateOfBirth(), user.getPhone(),
                user.getEmail(), user.getAddress(), user.getHashedPassword()));
            bw.newLine();
        }

        bw.close();
    }

    public static List<User> readUsers() throws IOException {
        List<User> list = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return list;

        BufferedReader br = new BufferedReader(new FileReader(file));

        // Bỏ qua dòng tiêu đề
        br.readLine();
        br.readLine();

        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            // Kiểm tra độ dài tối thiểu (132 ký tự)
            if (line.length() < 132) {
                System.err.println("Dòng dữ liệu không hợp lệ (quá ngắn): " + line);
                continue;
            }

            try {
                String fullName = line.substring(0, 20).trim();
                String dateOfBirth = line.substring(20, 40).trim();
                String phone = line.substring(40, 52).trim();
                String email = line.substring(52, 82).trim();
                String address = line.substring(82, 132).trim();
                String hashedPassword = line.substring(132).trim();

                list.add(new User(fullName, dateOfBirth, phone, email, address, hashedPassword));
            } catch (StringIndexOutOfBoundsException e) {
                System.err.println("Lỗi khi đọc dòng: " + line);
                e.printStackTrace();
            }
        }

        br.close();
        return list;
    }
}