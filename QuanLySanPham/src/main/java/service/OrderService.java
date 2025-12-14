package service;

import model.CartItem;
import model.Order;
import model.User;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderService implements IOrderService {

    @Override
    public boolean placeOrder(User user, List<CartItem> cart) {
        if (cart == null || cart.isEmpty()) return false;

        Connection conn = null;
        PreparedStatement psOrder = null;
        PreparedStatement psDetail = null;
        PreparedStatement psUpdateQty = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Tính tổng đơn
            double total = 0;
            for (CartItem ci : cart) {
                total += ci.getProduct().getPrice() * ci.getQuantity();
            }

            // Insert đơn hàng
            psOrder = conn.prepareStatement(
                    "INSERT INTO orders(user_id, total) VALUES(?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            psOrder.setInt(1, user.getId());
            psOrder.setDouble(2, total);
            psOrder.executeUpdate();

            rs = psOrder.getGeneratedKeys();
            int orderId = -1;
            if (rs.next()) orderId = rs.getInt(1);

            // Insert chi tiết đơn & trừ tồn kho
            psDetail = conn.prepareStatement(
                    "INSERT INTO order_details(order_id, product_id, quantity, price) VALUES(?,?,?,?)"
            );
            psUpdateQty = conn.prepareStatement(
                    "UPDATE products SET quantity = quantity - ? WHERE id = ? AND quantity >= ?"
            );

            for (CartItem ci : cart) {
                // Insert chi tiết đơn
                psDetail.setInt(1, orderId);
                psDetail.setInt(2, ci.getProduct().getId());
                psDetail.setInt(3, ci.getQuantity());
                psDetail.setDouble(4, ci.getProduct().getPrice() * ci.getQuantity());
                psDetail.executeUpdate();

                // Trừ tồn kho
                psUpdateQty.setInt(1, ci.getQuantity());
                psUpdateQty.setInt(2, ci.getProduct().getId());
                psUpdateQty.setInt(3, ci.getQuantity());
                int upd = psUpdateQty.executeUpdate();

                if (upd == 0) {
                    conn.rollback(); // tồn kho không đủ
                    return false;
                }
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
            }
            try {
                if (psOrder != null) psOrder.close();
            } catch (SQLException e) {
            }
            try {
                if (psDetail != null) psDetail.close();
            } catch (SQLException e) {
            }
            try {
                if (psUpdateQty != null) psUpdateQty.close();
            } catch (SQLException e) {
            }
            try {
                if (conn != null) conn.setAutoCommit(true);
                conn.close();
            } catch (Exception e) {
            }
        }
    }
    @Override
    public List<Order> getAll() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();

            boolean hasTotal = false;
            boolean hasTotalPrice = false;
            boolean hasCreatedAt = false;
            boolean hasOrderDate = false;
            for (int i = 1; i <= cols; i++) {
                String col = md.getColumnLabel(i).toLowerCase();
                if (col.equals("total")) hasTotal = true;
                if (col.equals("total_price")) hasTotalPrice = true;
                if (col.equals("created_at")) hasCreatedAt = true;
                if (col.equals("order_date")) hasOrderDate = true;
            }

            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                try { o.setUserId(rs.getInt("user_id")); } catch (SQLException ignored) {}

                // get total using whichever column exists
                double total = 0;
                if (hasTotal) {
                    total = rs.getDouble("total");
                } else if (hasTotalPrice) {
                    total = rs.getDouble("total_price");
                } else {
                    // fallback: try common names
                    try { total = rs.getDouble("total_price"); } catch (Exception ignored) {}
                }
                try { o.setTotalPrice(total); } catch (Exception ignored) {}

                // get created timestamp using available column name
                Timestamp ts = null;
                if (hasCreatedAt) {
                    ts = rs.getTimestamp("created_at");
                } else if (hasOrderDate) {
                    ts = rs.getTimestamp("order_date");
                } else {
                    try { ts = rs.getTimestamp("created_at"); } catch (Exception ignored) {}
                }
                try { o.setCreatedAt(ts); } catch (Exception ignored) {}

                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public void updateStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}