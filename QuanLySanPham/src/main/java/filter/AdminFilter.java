package filter;

import model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // SỬA LỖI: Lấy Session hiện tại (Nếu không có thì trả về null)
        HttpSession session = request.getSession(false);

        // Lấy đối tượng User từ Session
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("currentUser");
        }

        // 1. KIỂM TRA ĐĂNG NHẬP
        if (session == null || user == null) {
            // Debug thông báo chặn
            System.out.println("DEBUG FILTER: Access denied. Session or User is NULL. Redirecting to /login");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 2. KIỂM TRA VAI TRÒ
        if (user.getRoleId() == 1) {
            // Là Admin, cho phép truy cập
            System.out.println("DEBUG FILTER: Access granted for User ID: " + user.getId());
            filterChain.doFilter(request, response);
        } else {
            // Không phải Admin, chặn truy cập
            System.out.println("DEBUG FILTER: Access denied. Role ID: " + user.getRoleId());
            // Trả về lỗi 403 hoặc chuyển hướng về trang chủ
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập khu vực quản trị.");
            // Hoặc: response.sendRedirect(request.getContextPath() + "/");
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {}
    public void destroy() {}
}