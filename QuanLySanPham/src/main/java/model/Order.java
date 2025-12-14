package model;

import java.sql.Timestamp;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private Timestamp orderDate;
    private double totalAmount;
    private String status;
    private String shippingAddress;

    // ĐÃ THÊM TRƯỜNG NÀY ĐỂ KHẮC PHỤC LỖI JSP
    private String userName;

    private List<OrderDetail> details;

    // Constructor Đầy đủ (Dùng cho OrderDAO.getAllOrders)
    public Order(int id, int userId, Timestamp orderDate, double totalAmount, String status, String shippingAddress) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }

    // Constructor cho việc tạo đơn hàng mới (Dùng cho Checkout)
    public Order(int userId, double totalAmount, String shippingAddress) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
    }

    // --- Getters and Setters ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public List<OrderDetail> getDetails() { return details; }
    public void setDetails(List<OrderDetail> details) { this.details = details; }

    // GETTER/SETTER MỚI CHO TÊN NGƯỜI DÙNG
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}