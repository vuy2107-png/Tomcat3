package dao;

import model.Order;
import java.util.List;

public interface IOrderDAO {
    int createOrder(Order order) throws Exception;

    List<Order> getOrdersByUser(int userId) throws Exception;

    List<Order> getAllOrders() throws Exception;
}
