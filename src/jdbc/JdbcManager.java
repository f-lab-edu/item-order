package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcManager {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "dbfla1004";
    private static final String URL = "jdbc:mysql://localhost/order_item?useSSL=false&allowPublicKeyRetrieval=true";

    public JdbcManager() {
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load JDBC driver");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
