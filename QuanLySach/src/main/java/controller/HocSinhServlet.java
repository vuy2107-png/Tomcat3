package controller;

import dao.HocSinhDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/hocSinh")
public class HocSinhServlet extends HttpServlet {

    private HocSinhDAO dao = new HocSinhDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("list", dao.findAll());
        request.getRequestDispatcher("hocSinh/list.jsp")
                .forward(request, response);
    }
}
