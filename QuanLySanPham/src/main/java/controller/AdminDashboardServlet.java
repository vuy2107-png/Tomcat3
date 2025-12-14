package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Servlet này xử lý đường dẫn mà LoginServlet đã chuyển hướng đến
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Thiết lập các thuộc tính cần thiết cho trang dashboard (nếu có)
        // Ví dụ: thống kê số lượng người dùng, sản phẩm...

        // Chuyển tiếp (Forward) đến trang Dashboard JSP chính
        request.setAttribute("title", "Dashboard Quản trị");
        request.setAttribute("viewPath", "dashboard-content.jsp");

        // Chuyển tiếp tới trang admin-base.jsp (có header, sidebar, footer)
        request.getRequestDispatcher("/admin/admin-base.jsp").forward(request, response);
    }
}