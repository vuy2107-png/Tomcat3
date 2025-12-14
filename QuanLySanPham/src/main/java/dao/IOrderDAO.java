package dao;

import model.Order;
import model.OrderDetail;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IOrderDAO {

    // PHƯƠNG THỨC CHO CHECKOUT
    Optional<Integer> insertOrder(Order order, List<OrderDetail> details) throws SQLException;

    // PHƯƠNG THỨC CHO ADMIN PANEL
    List<Order> getAllOrders();
    List<OrderDetail> getOrderDetails(int orderId);
    boolean updateOrderStatus(int orderId, String newStatus) throws SQLException;
}