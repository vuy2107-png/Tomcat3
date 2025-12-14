package controller;

import model.Product;
import service.IProductService;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    private IProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "create":
                req.getRequestDispatcher("create.jsp").forward(req, resp);
                break;

            case "edit":
                showEdit(req, resp);
                break;

            case "view":
                showView(req, resp);
                break;

            case "delete":
                deleteProduct(req, resp);
                break;

            case "search":
                searchProduct(req, resp);
                break;

            default:
                listProduct(req, resp);
                break;
        }
    }

    private void listProduct(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> list = productService.getAll();
        req.setAttribute("products", list);
        req.getRequestDispatcher("product.jsp").forward(req, resp);
    }

    private void searchProduct(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        List<Product> products;
        if (keyword != null && !keyword.isEmpty()) {
            products = productService.search(keyword);
        } else {
            products = productService.getAll();
        }
        req.setAttribute("products", products);
        req.getRequestDispatcher("product.jsp").forward(req, resp);
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idS = req.getParameter("id");
        if (idS != null) {
            Product p = productService.getById(Integer.parseInt(idS));
            req.setAttribute("product", p);
            req.getRequestDispatcher("edit.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("product");
        }
    }

    private void showView(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idS = req.getParameter("id");
        if (idS != null) {
            Product p = productService.getById(Integer.parseInt(idS));
            req.setAttribute("product", p);
            req.getRequestDispatcher("view.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("product");
        }
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String idS = req.getParameter("id");
        if (idS != null && !idS.isEmpty()) {
            try {
                int id = Integer.parseInt(idS);
                boolean ok = productService.delete(id);
                if (ok) {
                    System.out.println("Đã xóa sản phẩm có id = " + id);
                } else {
                    System.err.println("Xóa thất bại cho id = " + id);
                }
            } catch (NumberFormatException e) {
                System.err.println("ID sản phẩm không hợp lệ: " + idS);
            }
        } else {
            System.err.println("Không nhận được ID để xóa.");
        }

        resp.sendRedirect(req.getContextPath() + "/product");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if ("delete".equalsIgnoreCase(action)) {
            deleteProduct(req, resp);
            return;
        }

        String idS = req.getParameter("id");
        String name = req.getParameter("name");
        String priceS = req.getParameter("price");
        String qtyS = req.getParameter("quantity");
        String desc = req.getParameter("description");

        double price = 0;
        int qty = 0;
        try { price = Double.parseDouble(priceS); } catch (Exception ignored) {}
        try { qty = Integer.parseInt(qtyS); } catch (Exception ignored) {}

        Product p = new Product();
        p.setName(name);
        p.setPrice(price);
        p.setQuantity(qty);
        p.setDescription(desc);

        if (idS == null || idS.isEmpty()) {
            productService.add(p);
        } else {
            p.setId(Integer.parseInt(idS));
            productService.update(p);
        }
        resp.sendRedirect(req.getContextPath() + "/product");
    }
}