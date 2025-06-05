package BackEnd_Hai;

import io.github.cdimascio.dotenv.Dotenv;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static final Dotenv dotenv = Dotenv.load();

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource dataSource;

    static {
        System.out.println("Connecting to database at " + dotenv.get("PG_HOST") + ":" + dotenv.get("PG_PORT") + "/" + dotenv.get("PG_DATABASE"));
        config.setJdbcUrl(String.format(
            "jdbc:postgresql://%s:%s/%s",
            dotenv.get("PG_HOST"),
            dotenv.get("PG_PORT"),
            dotenv.get("PG_DATABASE")
        ));
        config.setUsername(dotenv.get("PG_USER"));
        config.setPassword(dotenv.get("PG_PASSWORD"));
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
