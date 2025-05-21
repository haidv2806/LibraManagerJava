package BackEnd_Hai.newpackage;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {
        // Your code here
        System.out.println("Hello, World!");
        Database db = new Database();
        try {
            db.getConnection();
            System.out.println("Database connection successful!");
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }
}
