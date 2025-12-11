package dao;

import model.OrderDetail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class OrderDetailDAO implements IOrderDetailDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/shopclothes";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

    private static final String INSERT_DETAIL =
            "INSERT INTO order_details(order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

    protected Connection getConnection() throws Exception {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Override
    public void addOrderDetail(OrderDetail detail) throws Exception {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(INSERT_DETAIL);
        ps.setInt(1, detail.getOrderId());
        ps.setInt(2, detail.getProductId());
        ps.setInt(3, detail.getQuantity());
        ps.setDouble(4, detail.getPrice());
        ps.executeUpdate();
    }
}
