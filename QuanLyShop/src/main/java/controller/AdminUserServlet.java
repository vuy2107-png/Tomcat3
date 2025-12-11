package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "AdminUserServlet", urlPatterns = {
        "/admin/users",
        "/admin/users/delete"
})
public class AdminUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        switch (path) {
            case "/admin/users":
                request.getRequestDispatcher("/views/admin/user-list.jsp").forward(request, response);
                break;

            case "/admin/users/delete":
                // TODO delete user
                response.sendRedirect("/admin/users");
                break;
        }
    }
}
