package dao;

import model.Order;
import model.OrderDetail;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAO implements IOrderDAO {

    private static final String SELECT_ALL_ORDERS =
            "SELECT o.id, o.user_id, u.name AS user_name, o.order_date, o.total_amount, o.status, o.shipping_address " +
                    "FROM orders o JOIN users u ON o.user_id = u.id ORDER BY o.order_date DESC";

    private static final String UPDATE_ORDER_STATUS =
            "UPDATE orders SET status = ? WHERE id = ?"; // LỆNH SQL CHO CẬP NHẬT TRẠNG THÁI

    private static final String SELECT_ORDER_DETAILS_BY_ORDER_ID =
            "SELECT id, order_id, product_id, quantity, price FROM order_details WHERE order_id = ?";

    private static final String INSERT_ORDER_SQL =
            "INSERT INTO orders (user_id, total_amount, status, shipping_address) VALUES (?, ?, ?, ?)";
    private static final String INSERT_ORDER_DETAIL_SQL =
            "INSERT INTO order_details (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

    // ----------------------------------------------------------------------------------
    // PHƯƠNG THỨC CHO ADMIN PANEL (LIỆT KÊ)
    // ----------------------------------------------------------------------------------

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("order_date"),
                        rs.getDouble("total_amount"),
                        rs.getString("status"),
                        rs.getString("shipping_address")
                );
                // GÁN TÊN NGƯỜI DÙNG
                order.setUserName(rs.getString("user_name"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // ... (getOrderDetails) ...

    @Override
    public List<OrderDetail> getOrderDetails(int orderId) {
        List<OrderDetail> details = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_DETAILS_BY_ORDER_ID)) {

            preparedStatement.setInt(1, orderId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    details.add(new OrderDetail(
                            rs.getInt("id"),
                            rs.getInt("order_id"),
                            rs.getInt("product_id"),
                            rs.getInt("quantity"),
                            rs.getDouble("price")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }

    // ----------------------------------------------------------------------------------
    // PHƯƠNG THỨC CẬP NHẬT TRẠNG THÁI (CHO ADMIN)
    // ----------------------------------------------------------------------------------

    @Override
    public boolean updateOrderStatus(int orderId, String newStatus) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS)) {

            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, orderId);
            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    // ----------------------------------------------------------------------------------
    // PHƯƠNG THỨC CHO CHECKOUT (Logic Transaction)
    // ----------------------------------------------------------------------------------

    @Override
    public Optional<Integer> insertOrder(Order order, List<OrderDetail> details) throws SQLException {
        // ... (Logic Transaction đã cung cấp trước đó) ...
        int orderId = -1;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement orderStmt = connection.prepareStatement(INSERT_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, order.getUserId());
                orderStmt.setDouble(2, order.getTotalAmount());
                orderStmt.setString(3, "Pending");
                orderStmt.setString(4, order.getShippingAddress());

                int affectedRows = orderStmt.executeUpdate();
                if (affectedRows == 0) throw new SQLException("Thêm đơn hàng thất bại.");

                try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) orderId = generatedKeys.getInt(1);
                    else throw new SQLException("Thêm đơn hàng thất bại, không lấy được ID.");
                }
            }

            try (PreparedStatement detailStmt = connection.prepareStatement(INSERT_ORDER_DETAIL_SQL)) {
                for (OrderDetail detail : details) {
                    detailStmt.setInt(1, orderId);
                    detailStmt.setInt(2, detail.getProductId());
                    detailStmt.setInt(3, detail.getQuantity());
                    detailStmt.setDouble(4, detail.getPrice());
                    detailStmt.addBatch();
                }
                detailStmt.executeBatch();
            }

            connection.commit();
            return Optional.of(orderId);

        } catch (SQLException e) {
            if (connection != null) connection.rollback();
            e.printStackTrace();
            return Optional.empty();
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }
}