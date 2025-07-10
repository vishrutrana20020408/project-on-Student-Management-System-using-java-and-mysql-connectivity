package administration_management;

import java.sql.*;
import java.util.Base64;

public class UserDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/admin_mgmt";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Pawankumar@15";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static boolean isEmailOrPhoneExists(Connection conn, String email, String phone) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW TABLES");
        while (rs.next()) {
            String table = rs.getString(1);
            String query = "SELECT * FROM " + table + " WHERE email = ? OR phone = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, email);
                ps.setString(2, phone);
                ResultSet check = ps.executeQuery();
                if (check.next()) return true;
            } catch (SQLException ignored) {}
        }
        return false;
    }

    private static boolean doesTableExist(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData dbm = conn.getMetaData();
        ResultSet tables = dbm.getTables(null, null, tableName, null);
        return tables.next();
    }

    public static boolean isUsernameExists(Connection conn, String username, String tableName) throws SQLException {
        System.out.println("Checking username: " + username + " in table: " + tableName); // ADD THIS
        String query = "SELECT * FROM " + tableName + " WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public static String encryptPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public static void createTableIfNotExists(Connection conn, String tableName, String role) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql;
        if (role.equalsIgnoreCase("Teacher")) {
            sql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "name VARCHAR(255),"
                + "email VARCHAR(255),"
                + "phone VARCHAR(20),"
                + "course VARCHAR(100),"
                + "username VARCHAR(100) UNIQUE,"
                + "password VARCHAR(255))";
        } else {
            sql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "name VARCHAR(255),"
                + "email VARCHAR(255),"
                + "phone VARCHAR(20),"
                + "username VARCHAR(100) UNIQUE,"
                + "password VARCHAR(255))";
        }
        stmt.execute(sql);
    }

    public static void insertUser(String table, String[] fields, String[] values) throws SQLException {
        StringBuilder placeholders = new StringBuilder();
        StringBuilder columns = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            columns.append(fields[i]);
            placeholders.append("?");
            if (i < fields.length - 1) {
                columns.append(", ");
                placeholders.append(", ");
            }
        }

        String query = "INSERT INTO " + table + " (" + columns + ") VALUES (" + placeholders + ")";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            for (int i = 0; i < values.length; i++) {
                ps.setString(i + 1, values[i]);
            }
            ps.executeUpdate();
        }
    }


}
