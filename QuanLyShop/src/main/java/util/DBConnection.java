package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ShopClothes";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "123456";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            System.out.println("Kết nối thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Kết nối thất bại!");
        }
        return connection;
    }
}
