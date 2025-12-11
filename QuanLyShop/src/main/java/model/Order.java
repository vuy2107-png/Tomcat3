package model;

import java.sql.Timestamp;

public class Order {
    private int id;
    private int userId;     // <-- user_id từ bảng orders
    private double total;   // <-- total từ bảng orders
    private Timestamp createdAt;

    public Order() {}

    public Order(int id, int userId, double total, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.total = total;
        this.createdAt = createdAt;
    }

    // Getter - Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {          // <---
        return userId;
    }

    public void setUserId(int userId) {  // <---
        this.userId = userId;
    }

    public double getTotal() {        // <---
        return total;
    }

    public void setTotal(double total) {  // <---
        this.total = total;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
