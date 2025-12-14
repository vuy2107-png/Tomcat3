package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // Lấy session hiện tại (nếu có)

        if (session != null) {
            session.invalidate(); // Hủy toàn bộ Session (Xóa currentUser)
        }

        // Chuyển hướng về trang đăng nhập hoặc trang chủ
        response.sendRedirect(request.getContextPath() + "/login");
    }
}