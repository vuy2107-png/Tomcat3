package controller;

import dao.UserDAO;
import model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String successMessage = (String) request.getSession().getAttribute("successMessage");
        if (successMessage != null) {
            request.setAttribute("successMessage", successMessage);
            request.getSession().removeAttribute("successMessage");
        }

        request.getRequestDispatcher("/client/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Khắc phục lỗi: getUserByEmailAndPassword()
            Optional<User> userOptional = userDAO.getUserByEmailAndPassword(email, password);

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                // 1. Tạo Session
                HttpSession session = request.getSession();
                session.setAttribute("currentUser", user);
                session.setAttribute("userRole", user.getRoleId());

                // 2. Điều hướng (Role 1 là Admin)
                if (user.getRoleId() == 1) {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                    return; // <--- Đã BỔ SUNG: KẾT THÚC LUỒNG XỬ LÝ
                } else {
                    response.sendRedirect(request.getContextPath() + "/");
                    return; // <--- Đã BỔ SUNG: KẾT THÚC LUỒNG XỬ LÝ
                }
            } else {
                // Đăng nhập thất bại (chuyển về trang cũ)
                request.setAttribute("error", "Email hoặc mật khẩu không chính xác.");
                request.getRequestDispatcher("/client/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("/client/login.jsp").forward(request, response);
        }
    }
}