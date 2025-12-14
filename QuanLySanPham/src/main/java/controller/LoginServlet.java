package controller;

import model.User;
import service.IUserService;
import service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private IUserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String pass = req.getParameter("password");

        User u = userService.login(email, pass);

        if (u != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", u);

            // 🔹 Kiểm tra role và chuyển hướng phù hợp
            if ("admin".equalsIgnoreCase(u.getRole())) {
                resp.sendRedirect("admin");
            } else {
                resp.sendRedirect("product"); // Trang sản phẩm cho user
            }

        } else {
            req.setAttribute("error", "Email hoặc mật khẩu không đúng!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}