package controller;

import dao.IOrderDAO;
import dao.OrderDAO;
import model.Cart;
import model.CartItem; // THÊM IMPORT NÀY
import model.Order;
import model.OrderDetail;
import model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder; // THÊM IMPORT NÀY (Để khắc phục lỗi URLEncoder)
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    // Khai báo kiểu IOrderDAO, sử dụng OrderDAO để khởi tạo
    private final IOrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Chuyển hướng đến trang xác nhận thông tin địa chỉ (Tùy chọn)
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String message;

        // 1. Kiểm tra Đăng nhập
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login?redirect=/checkout");
            return;
        }

        User user = (User) session.getAttribute("currentUser");
        Cart cart = (Cart) session.getAttribute("cart");

        // 2. Kiểm tra Giỏ hàng
        if (cart == null || cart.getItems().isEmpty()) {
            message = URLEncoder.encode("Giỏ hàng rỗng! Vui lòng thêm sản phẩm.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/cart?error=" + message);
            return;
        }

        // 3. Lấy thông tin đặt hàng (Giả định lấy từ form POST)
        String shippingAddress = request.getParameter("shipping_address");
        if (shippingAddress == null || shippingAddress.isEmpty()) {
            shippingAddress = "Địa chỉ mặc định của người dùng"; // Xử lý nếu form thiếu
        }

        // 4. Chuẩn bị dữ liệu cho Order và OrderDetails
        double totalAmount = cart.getTotalAmount();
        Order order = new Order(user.getId(), totalAmount, shippingAddress);

        // Chuyển đổi CartItem sang OrderDetail
        List<OrderDetail> details = cart.getItems().stream()
                .map(item -> new OrderDetail(
                        0, // id (tự động tăng)
                        0, // orderId (sẽ được cập nhật sau khi Order chính được lưu)
                        item.getProduct().getId(),
                        item.getQuantity(),
                        item.getProduct().getPrice() // Giá tại thời điểm đặt hàng
                ))
                .collect(Collectors.toList());

        // 5. Thực hiện Transaction lưu đơn hàng (SỬ DỤNG insertOrder)
        try {
            Optional<Integer> orderId = orderDAO.insertOrder(order, details);

            if (orderId.isPresent()) {
                // Thành công: Xóa giỏ hàng và chuyển hướng
                session.removeAttribute("cart");
                message = URLEncoder.encode("Đặt hàng thành công! Mã đơn hàng: #" + orderId.get(), "UTF-8");
                response.sendRedirect(request.getContextPath() + "/order-confirmation?message=" + message);
            } else {
                // Thất bại: Hoàn tác đã xảy ra trong DAO
                message = URLEncoder.encode("Đặt hàng thất bại do lỗi hệ thống (Transaction Error).", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/checkout?error=" + message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message = URLEncoder.encode("Đặt hàng thất bại do lỗi Database.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/checkout?error=" + message);
        }
    }
}