package controller;

import model.Product;
import model.User;
import service.IProductService;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/products")
public class AdminProductServlet extends HttpServlet {
    private IProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");

        if ("create".equals(action)) {
            req.getRequestDispatcher("/create.jsp").forward(req, resp);
            return;
        }
        if ("edit".equals(action)) {
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    Product p = productService.getById(id);
                    req.setAttribute("product", p);
                    req.getRequestDispatcher("/edit.jsp").forward(req, resp);
                    return;
                } catch (NumberFormatException e) {
                }
            }
        }

        List<Product> products = productService.getAll();
        req.setAttribute("products", products);

        req.getRequestDispatcher("/admin_products.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    productService.delete(id);
                } catch (NumberFormatException ignored) {
                }
            }
        } else {
            String idStr = req.getParameter("id");
            String name = req.getParameter("name");
            String priceStr = req.getParameter("price");
            String quantityStr = req.getParameter("quantity");
            String description = req.getParameter("description");

            Product p = new Product();

            if (idStr != null && !idStr.isEmpty()) {
                try {
                    p.setId(Integer.parseInt(idStr));
                } catch (NumberFormatException ignored) {
                }
            }

            p.setName(name != null ? name.trim() : "");

            double price = 0;
            int qty = 0;
            try {
                if (priceStr != null && !priceStr.isEmpty()) {
                    price = Double.parseDouble(priceStr);
                }
            } catch (NumberFormatException ignored) {
            }
            try {
                if (quantityStr != null && !quantityStr.isEmpty()) {
                    qty = Integer.parseInt(quantityStr);
                }
            } catch (NumberFormatException ignored) {
            }

            p.setPrice(price);
            p.setQuantity(qty);
            p.setDescription(description != null ? description.trim() : "");

            if (idStr == null || idStr.isEmpty()) {
                productService.add(p);
            } else {
                productService.update(p);
            }
        }

        resp.sendRedirect(req.getContextPath() + "/admin/products");
    }
}