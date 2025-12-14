package controller;

import model.CartItem;
import model.User;
import service.IOrderService;
import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private IOrderService orderService = new OrderService();

    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("order.jsp").forward(req, resp);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (user == null) {
            resp.sendRedirect("login"); // chưa đăng nhập
            return;
        }

        if (cart == null || cart.isEmpty()) {
            req.setAttribute("error", "Giỏ hàng trống!");
            req.getRequestDispatcher("cart.jsp").forward(req, resp);
            return;
        }

        boolean ok = orderService.placeOrder(user, cart);
        if (ok) {
            session.removeAttribute("cart"); // xóa giỏ hàng
            req.setAttribute("success", "Đặt hàng thành công!");
        } else {
            req.setAttribute("error", "Đặt hàng thất bại! Kiểm tra tồn kho sản phẩm.");
        }

        req.getRequestDispatcher("order.jsp").forward(req, resp);
    }
}