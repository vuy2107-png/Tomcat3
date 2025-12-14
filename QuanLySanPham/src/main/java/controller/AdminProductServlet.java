package controller;

import dao.CategoryDAO;
import dao.IProductDAO;
import dao.ProductDAO;
import model.Category;
import model.Product;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {"/admin/products", "/admin/products/*"})
public class AdminProductServlet extends HttpServlet {

    private final IProductDAO productDAO = new ProductDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO(); // KHỞI TẠO CATEGORY DAO

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteProduct(request, response);
                    break;
                case "list":
                default:
                    listProducts(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error in doGet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "insert";
        }

        try {
            switch (action) {
                case "insert":
                    insertProduct(request, response);
                    break;
                case "update":
                    updateProduct(request, response);
                    break;
                default:
                    listProducts(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error in doPost", e);
        }
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Product> list = productDAO.getAllProducts();
        request.setAttribute("products", list);
        request.setAttribute("title", "Quản lý Sản phẩm");
        request.setAttribute("viewPath", "product-list.jsp");
        request.getRequestDispatcher("/admin/admin-base.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy danh sách danh mục để hiển thị trong <select>
        List<Category> categories = categoryDAO.getAllCategories();
        request.setAttribute("categories", categories);

        request.setAttribute("title", "Thêm Sản phẩm Mới");
        request.setAttribute("viewPath", "product-form.jsp");
        request.getRequestDispatcher("/admin/admin-base.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Optional<Product> existingProduct = productDAO.getProductById(id);

        // Lấy danh sách danh mục để hiển thị trong <select>
        List<Category> categories = categoryDAO.getAllCategories();
        request.setAttribute("categories", categories);

        if (existingProduct.isPresent()) {
            request.setAttribute("product", existingProduct.get());
            request.setAttribute("title", "Chỉnh sửa Sản phẩm");
            request.setAttribute("viewPath", "product-form.jsp"); // Dùng chung form cho cả New và Edit
            request.getRequestDispatcher("/admin/admin-base.jsp").forward(request, response);
        } else {
            String message = URLEncoder.encode("Không tìm thấy sản phẩm cần chỉnh sửa!", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/admin/products?error=" + message);
        }
    }

    // PHƯƠNG THỨC XỬ LÝ DỮ LIỆU CHUNG (QUAN TRỌNG: SỬA LỖI 500)
    private Product extractProductFromRequest(HttpServletRequest request, int id) throws NumberFormatException {
        // Lấy dữ liệu thô
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        String quantityStr = request.getParameter("quantity");
        String description = request.getParameter("description");
        String imageUrl = request.getParameter("image_url");
        String categoryIdStr = request.getParameter("category_id");

        // LÀM SẠCH VÀ CHUYỂN ĐỔI: Sửa lỗi Data Truncation
        // Loại bỏ dấu phân cách (dấu phẩy, dấu cách) để chỉ còn số và dấu chấm thập phân
        priceStr = priceStr.replaceAll("[^0-9.]", "");

        double price = Double.parseDouble(priceStr);
        int quantity = Integer.parseInt(quantityStr);
        int categoryId = Integer.parseInt(categoryIdStr);

        return new Product(id, name, price, quantity, description, imageUrl, categoryId);
    }


    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String message;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Product product = extractProductFromRequest(request, id);

            boolean updated = productDAO.updateProduct(product);

            if (updated) {
                message = URLEncoder.encode("Cập nhật sản phẩm thành công!", "UTF-8");
            } else {
                message = URLEncoder.encode("Cập nhật sản phẩm thất bại (DB Error)!", "UTF-8");
            }
            response.sendRedirect(request.getContextPath() + "/admin/products?message=" + message);

        } catch (NumberFormatException e) {
            message = URLEncoder.encode("Lỗi: Giá, Số lượng hoặc Danh mục không hợp lệ.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/admin/products?error=" + message);
        }
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String message;
        try {
            Product newProduct = extractProductFromRequest(request, 0); // ID = 0 cho Insert
            productDAO.insertProduct(newProduct);

            message = URLEncoder.encode("Thêm sản phẩm thành công!", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/admin/products?message=" + message);

        } catch (NumberFormatException e) {
            message = URLEncoder.encode("Lỗi: Giá, Số lượng hoặc Danh mục không hợp lệ.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/admin/products?error=" + message);
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String message;

        try {
            productDAO.deleteProduct(id);
            message = URLEncoder.encode("Xóa sản phẩm thành công!", "UTF-8");
        } catch (SQLException e) {
            // Mã lỗi 1451 là mã lỗi phổ biến của MySQL cho việc vi phạm Khóa ngoại
            if (e.getErrorCode() == 1451) {
                message = URLEncoder.encode("Xóa thất bại! Sản phẩm này đã có trong Đơn hàng (Khóa ngoại).", "UTF-8");
            } else {
                message = URLEncoder.encode("Xóa thất bại! Lỗi Database: " + e.getMessage(), "UTF-8");
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/products?message=" + message);
    }
}