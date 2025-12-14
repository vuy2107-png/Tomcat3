package controller;

import model.Order;
import model.User;
import service.IOrderService;
import service.OrderService;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/orders")
public class AdminOrderServlet extends HttpServlet {
    private IOrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");
        if ("updateStatus".equals(action)) {
            String idStr = req.getParameter("id");
            String status = req.getParameter("status");
            if (idStr != null && status != null) {
                int orderId = Integer.parseInt(idStr);
                orderService.updateStatus(orderId, status);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/orders");
            return;
        }
        List<Order> orders = orderService.getAll();
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/admin_orders.jsp").forward(req, resp);
    }
}