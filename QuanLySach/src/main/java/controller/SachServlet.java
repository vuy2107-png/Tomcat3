package controller;

import dao.SachDAO;
import model.Sach;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/sach")
public class SachServlet extends HttpServlet {

    private SachDAO dao = new SachDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("listSach", dao.findAll());
        request.getRequestDispatcher("sach/list.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            Sach s = new Sach(
                    0,
                    request.getParameter("tenSach"),
                    request.getParameter("tacGia"),
                    request.getParameter("moTa"),
                    Integer.parseInt(request.getParameter("soLuong"))
            );
            dao.insert(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Số lượng không hợp lệ.");
            return;
        }

        response.sendRedirect("sach");
    }
}