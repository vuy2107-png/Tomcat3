package controller;

import dao.OrderDAO;
import dao.ProductDAO;
import model.Order;
import model.OrderDetail;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/orders")
public class AdminOrderServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();
    private final ProductDAO productDAO = new ProductDAO(); // Cần cho order-details.jsp nếu sử dụng scriptlet

    // ... (Logic init và doGet) ...
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "view":
                    viewOrderDetails(request, response);
                    break;
                case "list":
                default:
                    listOrders(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    // ----------------------------------------------------------------------------------
    // PHƯƠNG THỨC MỚI CHO CẬP NHẬT TRẠNG THÁI (DÙNG POST METHOD)
    // ----------------------------------------------------------------------------------
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("update_status".equals(action)) {
                updateOrderStatus(request, response);
            } else {
                // Nếu không có action POST nào khác, chuyển hướng về danh sách
                response.sendRedirect(request.getContextPath() + "/admin/orders");
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<Order> list = orderDAO.getAllOrders();
        request.setAttribute("orders", list);
        request.setAttribute("title", "Quản lý Đơn hàng");
        request.setAttribute("viewPath", "order-list.jsp");
        request.getRequestDispatcher("/admin/admin-base.jsp").forward(request, response);
    }

    private void viewOrderDetails(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int orderId = Integer.parseInt(request.getParameter("id"));
        List<OrderDetail> details = orderDAO.getOrderDetails(orderId);

        request.setAttribute("orderDetails", details);
        request.setAttribute("orderId", orderId);
        request.setAttribute("title", "Chi tiết Đơn hàng #" + orderId);
        request.setAttribute("viewPath", "order-details.jsp");
        request.getRequestDispatcher("/admin/admin-base.jsp").forward(request, response);
    }

    // PHƯƠNG THỨC XỬ LÝ POST
    private void updateOrderStatus(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        try {
            int orderId = Integer.parseInt(request.getParameter("id"));
            String newStatus = request.getParameter("status");

            boolean success = orderDAO.updateOrderStatus(orderId, newStatus);

            String message;
            if (success) {
                message = URLEncoder.encode("Cập nhật trạng thái đơn hàng #" + orderId + " thành công!", "UTF-8");
            } else {
                message = URLEncoder.encode("Cập nhật thất bại.", "UTF-8");
            }

            // Chuyển hướng về trang chi tiết đơn hàng sau khi cập nhật
            response.sendRedirect(request.getContextPath() + "/admin/orders?action=view&id=" + orderId + "&message=" + message);

        } catch (NumberFormatException e) {
            String message = URLEncoder.encode("ID đơn hàng không hợp lệ.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/admin/orders?error=" + message);
        }
    }
}