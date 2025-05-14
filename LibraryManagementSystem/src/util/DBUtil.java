package util;

import java.sql.*;

public class DBUtil {
    static {
        try {
            // Newer versions use the same driver class
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to register MySQL driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/library_db?useSSL=false&serverTimezone=UTC";
        String user = "root";       // your username
        String password = "";       // your password
        return DriverManager.getConnection(url, user, password);
    }
}