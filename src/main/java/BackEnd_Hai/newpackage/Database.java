package BackEnd_Hai.newpackage;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String URL = String.format(
        "jdbc:postgresql://%s:%s/%s",
        dotenv.get("PG_HOST"),
        dotenv.get("PG_PORT"),
        dotenv.get("PG_DATABASE")
    );
    private static final String USER = dotenv.get("PG_USER");
    private static final String PASSWORD = dotenv.get("PG_PASSWORD");

    public static Connection getConnection() throws SQLException {
        System.out.println("Connecting to database...");
        System.out.println("URL: " + URL);
        System.out.println("User: " + USER);
        System.out.println("Password: " + PASSWORD);
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
