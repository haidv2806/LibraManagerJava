package com.javateam.libramanagerjava;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Users.insertUser(
            "Nguyen Van K",
            "123456",
            "0911223344",
            "k.nguyen@example.com",
            "123 Duong ABC, Ha Noi"
        );
    }
}