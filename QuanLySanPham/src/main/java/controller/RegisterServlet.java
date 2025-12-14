package controller;

import dao.UserDAO;
import model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/client/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        String message = "";

        try {
            // Kiểm tra Email đã tồn tại
            if (userDAO.isEmailExists(email)) {
                message = "Email này đã được sử dụng. Vui lòng chọn email khác.";
                request.setAttribute("error", message);
                request.setAttribute("name", name);
                request.setAttribute("email", email);
                request.getRequestDispatcher("/client/register.jsp").forward(request, response);
                return;
            }

            // Tạo đối tượng User - Đã KHẮC PHỤC LỖI CONSTRUCTOR
            // roleId = 2 cho Khách hàng thông thường (Giả định)
            User newUser = new User(email, password, name, 2, true);

            // Lưu vào Database
            Optional<Integer> newId = userDAO.insertUser(newUser);

            if (newId.isPresent()) {
                message = "Đăng ký thành công! Vui lòng đăng nhập.";
                request.getSession().setAttribute("successMessage", message);
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                message = "Đăng ký thất bại do lỗi hệ thống (Không lấy được ID).";
                request.setAttribute("error", message);
                request.getRequestDispatcher("/client/register.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            message = "Lỗi Database: " + e.getMessage();
            request.setAttribute("error", message);
            request.getRequestDispatcher("/client/register.jsp").forward(request, response);
        }
    }
}