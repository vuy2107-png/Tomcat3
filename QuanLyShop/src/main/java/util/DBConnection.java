package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // THAY ĐỔI TỪ localhost SANG 127.0.0.1
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/ShopClothes";

    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "123456"; // Giữ nguyên mật khẩu bạn đã xác nhận

    private static final String CONFIG_PARAMS = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Lỗi: Không tìm thấy MySQL JDBC Driver!");
            throw new SQLException("Không tìm thấy MySQL JDBC Driver.", e);
        }

        Connection connection = DriverManager.getConnection(JDBC_URL + CONFIG_PARAMS, JDBC_USERNAME, JDBC_PASSWORD);

        System.out.println("Kết nối thành công tới database ShopClothes!");

        return connection;
    }
}