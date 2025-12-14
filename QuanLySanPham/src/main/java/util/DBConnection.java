package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // CHỈ CHỨA URL CƠ BẢN VÀ TÊN DATABASE
    // Đã sửa: sử dụng đúng tên database đã tạo: ShopClothes
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/ShopClothes";
    private static final String JDBC_USERNAME = "root";
    // KIỂM TRA LẠI MẬT KHẨU NÀY MỘT LẦN NỮA:
    private static final String JDBC_PASSWORD = "123456";

    // CẤU HÌNH CONFIG: Đã thêm ký tự "?" ở đầu để nối đúng vào URL
    private static final String CONFIG_PARAMS =
            "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";

    public static Connection getConnection() throws SQLException {
        try {
            // Tải Driver (Khuyến khích giữ lại)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Lỗi: Không tìm thấy MySQL JDBC Driver!");
            throw new SQLException("Lỗi cấu hình Driver.", e);
        }

        // URL HOÀN CHỈNH: Trả về JDBC_URL + CONFIG_PARAMS
        String fullJdbcUrl = JDBC_URL + CONFIG_PARAMS;
        System.out.println("DEBUG CONNECTION URL: " + fullJdbcUrl); // Dùng để debug

        return DriverManager.getConnection(fullJdbcUrl, JDBC_USERNAME, JDBC_PASSWORD);
    }
}