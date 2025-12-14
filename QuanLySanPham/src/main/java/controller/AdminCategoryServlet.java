package controller;

import dao.CategoryDAO;
import model.Category;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {"/admin/categories", "/admin/categories/*"})
public class AdminCategoryServlet extends HttpServlet {

    private final CategoryDAO categoryDAO = new CategoryDAO();

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
                    deleteCategory(request, response);
                    break;
                case "list":
                default:
                    listCategories(request, response);
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
                    insertCategory(request, response);
                    break;
                case "update":
                    updateCategory(request, response);
                    break;
                default:
                    listCategories(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error in doPost", e);
        }
    }

    // READ
    private void listCategories(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Category> list = categoryDAO.getAllCategories();
        request.setAttribute("categories", list);
        request.setAttribute("title", "Quản lý Danh mục");
        request.setAttribute("viewPath", "category-list.jsp");
        request.getRequestDispatcher("/admin/admin-base.jsp").forward(request, response);
    }

    // SHOW NEW FORM
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("title", "Thêm Danh mục Mới");
        request.setAttribute("viewPath", "category-form.jsp");
        request.getRequestDispatcher("/admin/admin-base.jsp").forward(request, response);
    }

    // SHOW EDIT FORM
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Optional<Category> existingCategory = categoryDAO.getCategoryById(id);

        if (existingCategory.isPresent()) {
            request.setAttribute("category", existingCategory.get());
            request.setAttribute("title", "Chỉnh sửa Danh mục");
            request.setAttribute("viewPath", "category-form.jsp");
            request.getRequestDispatcher("/admin/admin-base.jsp").forward(request, response);
        } else {
            String message = URLEncoder.encode("Không tìm thấy danh mục cần chỉnh sửa!", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/admin/categories?error=" + message);
        }
    }

    // CREATE
    private void insertCategory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        String name = request.getParameter("name");
        Category newCategory = new Category(0, name);

        categoryDAO.insertCategory(newCategory);

        String message = URLEncoder.encode("Thêm danh mục thành công!", "UTF-8");
        response.sendRedirect(request.getContextPath() + "/admin/categories?message=" + message);
    }

    // UPDATE
    private void updateCategory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");

        Category category = new Category(id, name);
        boolean updated = categoryDAO.updateCategory(category);

        String message;
        if (updated) {
            message = URLEncoder.encode("Cập nhật danh mục thành công!", "UTF-8");
        } else {
            message = URLEncoder.encode("Cập nhật danh mục thất bại!", "UTF-8");
        }

        response.sendRedirect(request.getContextPath() + "/admin/categories?message=" + message);
    }

    // DELETE
    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String message;

        try {
            categoryDAO.deleteCategory(id);
            message = URLEncoder.encode("Xóa danh mục thành công!", "UTF-8");
        } catch (SQLException e) {
            // Mã lỗi 1451: Lỗi Khóa ngoại (Danh mục đang được Sản phẩm sử dụng)
            if (e.getErrorCode() == 1451) {
                message = URLEncoder.encode("Xóa thất bại! Danh mục đang chứa Sản phẩm.", "UTF-8");
            } else {
                message = URLEncoder.encode("Xóa thất bại! Lỗi Database: " + e.getMessage(), "UTF-8");
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/categories?message=" + message);
    }
}