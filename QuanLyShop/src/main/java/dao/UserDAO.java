package dao;

import model.User;
import util.DBConnection; // Giả định class này có hàm getConnection()

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDAO implements IUserDAO {

    private static final String INSERT_SQL =
            "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM users WHERE id = ?"; // Bổ sung SQL
    private static final String SELECT_BY_EMAIL_SQL =
            "SELECT * FROM users WHERE email = ?";
    private static final String SELECT_ALL_SQL =
            "SELECT * FROM users";
    private static final String UPDATE_SQL =
            "UPDATE users SET name = ?, password = ?, role = ? WHERE id = ?";
    private static final String DELETE_SQL =
            "DELETE FROM users WHERE id = ?";

    @Override
    public void addUser(User user) throws Exception { // Đã sửa: dùng nội dung hàm insertUser cũ
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.executeUpdate();
        }
    }

    @Override
    public User getUserById(int id) throws Exception { // Đã sửa: triển khai đầy đủ
        User user = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws Exception {
        return null;
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        return Collections.emptyList();
    }

    @Override
    public void updateUser(User user) throws Exception {

    }

    @Override
    public void deleteUser(int id) throws Exception {

    }

    // Các hàm khác như getUserByEmail, getAllUsers, updateUser, deleteUser giữ nguyên...
    // ...
}