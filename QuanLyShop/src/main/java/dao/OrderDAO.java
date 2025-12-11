package dao;

import model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements IOrderDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/shopclothes";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

    private static final String INSERT_ORDER = "INSERT INTO orders(user_id, total) VALUES (?, ?)";
    private static final String SELECT_BY_USER = "SELECT * FROM orders WHERE user_id = ?";
    private static final String SELECT_ALL = "SELECT * FROM orders";

    protected Connection getConnection() throws Exception {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Override
    public int createOrder(Order order) throws Exception {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, order.getUserId());
        ps.setDouble(2, order.getTotal());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1); // trả về id đơn hàng vừa tạo
        }
        return -1;
    }

    @Override
    public List<Order> getOrdersByUser(int userId) throws Exception {
        List<Order> list = new ArrayList<>();

        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(SELECT_BY_USER);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Order(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getDouble("total"),
                    rs.getTimestamp("created_at")
            ));
        }
        return list;
    }

    @Override
    public List<Order> getAllOrders() throws Exception {
        List<Order> list = new ArrayList<>();

        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(SELECT_ALL);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Order(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getDouble("total"),
                    rs.getTimestamp("created_at")
            ));
        }
        return list;
    }
}
