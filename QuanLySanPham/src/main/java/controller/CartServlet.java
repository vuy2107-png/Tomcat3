package controller;

import model.CartItem;
import model.Product;
import service.IProductService;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private IProductService productService = new ProductService();

    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        Object o = session.getAttribute("cart");
        if (o == null) {
            List<CartItem> cart = new ArrayList<>();
            session.setAttribute("cart", cart);
            return cart;
        } else {
            return (List<CartItem>) o;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        List<CartItem> cart = getCart(session);

        if (action == null || "view".equals(action)) {
            req.getRequestDispatcher("/cart.jsp").forward(req, resp);
            return;
        }

        if ("remove".equals(action)) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                cart.removeIf(ci -> ci.getProduct().getId() == id);
            } catch (NumberFormatException ignored) {
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        if ("update".equals(action)) {
            for (CartItem ci : new ArrayList<>(cart)) {
                String param = "quantity_" + ci.getProduct().getId();
                String qS = req.getParameter(param);
                if (qS != null) {
                    try {
                        int q = Integer.parseInt(qS);
                        if (q <= 0) cart.remove(ci);
                        else if (q <= ci.getProduct().getQuantity()) ci.setQuantity(q);
                        else ci.setQuantity(ci.getProduct().getQuantity());
                    } catch (Exception e) {
                    }
                }
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            int id;
            try {
                id = Integer.parseInt(req.getParameter("id"));
            } catch (NumberFormatException nfe) {
                resp.sendRedirect(req.getContextPath() + "/product");
                return;
            }

            HttpSession session = req.getSession();
            List<CartItem> cart = getCart(session);

            Product p = productService.getById(id);
            if (p != null && p.getQuantity() > 0) {
                boolean found = false;
                for (CartItem it : cart) {
                    if (it.getProduct().getId() == p.getId()) {
                        if (it.getQuantity() < p.getQuantity()) {
                            it.setQuantity(it.getQuantity() + 1);
                        }
                        found = true;
                        break;
                    }
                }
                if (!found && p.getQuantity() > 0) {
                    cart.add(new CartItem(p, 1));
                }
            }
            resp.sendRedirect(req.getContextPath() + "/product?success=added");
        }
    }
}