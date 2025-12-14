package controller;

import dao.UserDAO;
import model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "new":
                    showNewForm(request, response); // Khắc phục lỗi: Cannot resolve method 'showNewForm'
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteUser(request, response); // Đồng bộ DAO
                    break;
                case "list":
                default:
                    listUsers(request, response); // Đồng bộ DAO
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "insert";
        }

        try {
            switch (action) {
                case "insert":
                    insertUser(request, response);
                    break;
                case "update":
                    updateUser(request, response); // Đồng bộ DAO
                    break;
                default:
                    listUsers(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<User> list = userDAO.getAllUsers(); // Khắc phục lỗi: cannot find symbol method getAllUsers()

        request.setAttribute("users", list);
        request.setAttribute("title", "Quản lý Người dùng");
        request.setAttribute("viewPath", "user-list.jsp");
        request.getRequestDispatcher("/admin/admin-base.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("user", new User());
        request.setAttribute("title", "Thêm Người dùng Mới");
        request.setAttribute("viewPath", "user-form.jsp");
        request.getRequestDispatcher("/admin/admin-base.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Optional<User> existingUser = userDAO.getUserById(id);

        if (existingUser.isPresent()) {
            request.setAttribute("user", existingUser.get());
            request.setAttribute("title", "Sửa Người dùng");
            request.setAttribute("viewPath", "user-form.jsp");
            request.getRequestDispatcher("/admin/admin-base.jsp").forward(request, response);
        } else {
            response.sendRedirect("users");
        }
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        boolean isActive = "on".equals(request.getParameter("isActive"));

        User newUser = new User(email, password, name, roleId, isActive);
        userDAO.insertUser(newUser);
        response.sendRedirect("users");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        boolean isActive = "on".equals(request.getParameter("isActive"));

        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setRoleId(roleId);
        user.setActive(isActive);

        userDAO.updateUser(user); // Khắc phục lỗi: updatePassword(model.User)
        response.sendRedirect("users");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        userDAO.deleteUser(id); // Khắc phục lỗi: deleteUser(int)
        response.sendRedirect("users");
    }
}