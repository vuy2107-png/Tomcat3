package service;

import model.CartItem;
import model.Order;
import model.User;

import java.util.List;

public interface IOrderService {
    boolean placeOrder(User user, List<CartItem> cart);
    List<Order> getAll();
    void updateStatus(int orderId, String status);

}