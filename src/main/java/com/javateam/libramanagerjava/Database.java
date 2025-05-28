package com.javateam.libramanagerjava;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    // Gán trực tiếp thông tin kết nối
    private static final String PG_HOST = "localhost";
    private static final String PG_PORT = "5432";
    private static final String PG_DATABASE = "DatabasecuaHan";
    private static final String PG_USER = "postgres";
    private static final String PG_PASSWORD = "vunghan@11";

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource dataSource;

    static {
        config.setJdbcUrl(String.format(
            "jdbc:postgresql://%s:%s/%s",
            PG_HOST,
            PG_PORT,
            PG_DATABASE
        ));
        config.setUsername(PG_USER);
        config.setPassword(PG_PASSWORD);
        config.setMaximumPoolSize(10); // số kết nối tối đa
        config.setMinimumIdle(2);      // số kết nối rảnh tối thiểu
        config.setIdleTimeout(30000);  // timeout cho idle connection (ms)
        config.setMaxLifetime(600000); // thời gian sống của connection (ms)
        config.setConnectionTimeout(30000); // timeout khi lấy connection (ms)

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}