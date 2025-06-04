package HanXacThucNguoiDung;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource dataSource;

    static {
        try {
            Properties props = new Properties();
            // Kiểm tra file application.properties
            java.io.InputStream inStream = Database.class.getClassLoader().getResourceAsStream("application.properties");
            if (inStream == null) {
                // Dùng giá trị mặc định nếu file không tồn tại
                props.setProperty("db.host", "localhost");
                props.setProperty("db.port", "5432");
                props.setProperty("db.database", "java");
                props.setProperty("db.user", "postgres");
                props.setProperty("db.password", "Haidv2806");
            } else {
                props.load(inStream);
            }
            config.setJdbcUrl(String.format(
                "jdbc:postgresql://%s:%s/%s",
                props.getProperty("db.host", "localhost"),
                props.getProperty("db.port", "5432"),
                props.getProperty("db.database", "java")
            ));
            config.setUsername(props.getProperty("db.user", "postgres"));
            config.setPassword(props.getProperty("db.password", "Haidv2806"));
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setMaxLifetime(600000);
            config.setConnectionTimeout(30000);
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
     
    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}