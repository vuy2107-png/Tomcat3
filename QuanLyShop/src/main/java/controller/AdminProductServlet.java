package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "AdminProductServlet", urlPatterns = {
        "/admin/products",
        "/admin/products/add",
        "/admin/products/edit",
        "/admin/products/delete"
})
public class AdminProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        switch (path) {
            case "/admin/products":
                request.getRequestDispatcher("/views/admin/product-list.jsp").forward(request, response);
                break;

            case "/admin/products/add":
                request.getRequestDispatcher("/views/admin/product-add.jsp").forward(request, response);
                break;

            case "/admin/products/edit":
                request.getRequestDispatcher("/views/admin/product-edit.jsp").forward(request, response);
                break;

            case "/admin/products/delete":
                // TODO: gọi DAO xóa
                response.sendRedirect("/admin/products");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        switch (path) {
            case "/admin/products/add":
                // TODO: xử lý thêm product
                break;

            case "/admin/products/edit":
                // TODO: xử lý update product
                break;
        }

        response.sendRedirect("/admin/products");
    }
}
