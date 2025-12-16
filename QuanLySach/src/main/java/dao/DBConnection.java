package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL =
            "jdbc:mysql://localhost:3306/quanlysach?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "123456";

    // 1. Dùng cho các thao tác đọc hoặc insert đơn lẻ (Auto-commit = true)
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 2. Dùng cho GIAO DỊCH (Transaction)
    public static Connection getTransactionConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            conn.setAutoCommit(false); // Tắt Auto-commit để quản lý thủ công
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Lỗi Driver JDBC");
        }
    }
}