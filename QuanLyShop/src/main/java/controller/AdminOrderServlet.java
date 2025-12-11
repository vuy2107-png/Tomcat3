package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "AdminOrderServlet", urlPatterns = {
        "/admin/orders",
        "/admin/orders/detail"
})
public class AdminOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        switch (path) {
            case "/admin/orders":
                request.getRequestDispatcher("/views/admin/order-list.jsp").forward(request, response);
                break;

            case "/admin/orders/detail":
                request.getRequestDispatcher("/views/admin/order-detail.jsp").forward(request, response);
                break;
        }
    }
}
