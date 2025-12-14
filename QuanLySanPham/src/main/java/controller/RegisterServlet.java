package controller;

import model.User;
import service.IUserService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private IUserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPassword(password);

        boolean ok = userService.register(u);
        if (ok) {
            resp.sendRedirect("login");
        } else {
            req.setAttribute("error", "Đăng ký thất bại. Email có thể đã tồn tại.");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }
}