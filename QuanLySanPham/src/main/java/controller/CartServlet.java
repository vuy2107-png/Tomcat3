package controller;

import dao.IProductDAO;
import dao.ProductDAO;
import model.CartItem;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet(urlPatterns = {"/cart", "/add-to-cart", "/update-cart", "/remove-from-cart"})
public class CartServlet extends HttpServlet {

    private final IProductDAO productDAO = new ProductDAO();

    // Phương thức chính để lấy giỏ hàng từ Session
    private Map<Integer, CartItem> getCart(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        // Giỏ hàng được lưu dưới dạng Map<ProductID, CartItem>
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // GET /cart: Hiển thị trang giỏ hàng
        request.setAttribute("cartItems", getCart(request).values());
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String servletPath = request.getServletPath();

        try {
            if ("/add-to-cart".equals(servletPath)) {
                addToCart(request, response);
            } else if ("/update-cart".equals(servletPath)) {
                updateCart(request, response);
            } else if ("/remove-from-cart".equals(servletPath)) {
                removeFromCart(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error in CartServlet", e);
        }
    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = 1; // Mặc định thêm 1 sản phẩm

        Map<Integer, CartItem> cart = getCart(request);
        Optional<Product> productOpt = productDAO.getProductById(productId);

        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            if (cart.containsKey(productId)) {
                // Sản phẩm đã có: Tăng số lượng
                CartItem item = cart.get(productId);
                item.setQuantity(item.getQuantity() + quantity);
            } else {
                // Sản phẩm chưa có: Thêm mới
                cart.put(productId, new CartItem(product, quantity));
            }
        }
        // Chuyển hướng về trang giỏ hàng
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    // Cần thêm các phương thức updateCart và removeFromCart (Tạm bỏ qua để tập trung vào Checkout)
    private void updateCart(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // ... (Logic cập nhật số lượng)
    }

    private void removeFromCart(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // ... (Logic xóa sản phẩm khỏi giỏ)
    }
}