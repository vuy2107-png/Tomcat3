package dao;

import model.User;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO {

    // Đã đồng bộ các tên cột: role_id và is_active
    private static final String SELECT_ALL_USERS = "SELECT id, email, password, name, role_id, is_active, created_at FROM users ORDER BY created_at DESC";
    private static final String SELECT_USER_BY_ID = "SELECT id, email, password, name, role_id, is_active, created_at FROM users WHERE id = ?";
    private static final String INSERT_USER_SQL = "INSERT INTO users (email, password, name, role_id, is_active) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_SQL = "UPDATE users SET email = ?, password = ?, name = ?, role_id = ?, is_active = ? WHERE id = ?";
    private static final String DELETE_USER_SQL = "DELETE FROM users WHERE id = ?";
    private static final String CHECK_EMAIL_EXISTS = "SELECT COUNT(*) FROM users WHERE email = ?";
    private static final String SELECT_USER_BY_EMAIL_AND_PASSWORD =
            "SELECT id, email, password, name, role_id, is_active, created_at FROM users WHERE email = ? AND password = ? AND is_active = TRUE";

    public List<User> getAllUsers() throws SQLException { // Khắc phục lỗi: cannot find symbol method getAllUsers()
        List<User> users = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getInt("role_id"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("created_at")
                ));
            }
        }
        return users;
    }

    public Optional<User> getUserByEmailAndPassword(String email, String password) throws SQLException { // Khắc phục lỗi: getUserByEmailAndPassword()
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_AND_PASSWORD)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getInt("role_id"),
                            rs.getBoolean("is_active"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        }
        return Optional.empty();
    }

    public boolean updateUser(User user) throws SQLException { // Khắc phục lỗi: updatePassword(model.User)
        boolean rowUpdated;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL)) {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setInt(4, user.getRoleId());
            preparedStatement.setBoolean(5, user.isActive());
            preparedStatement.setInt(6, user.getId());

            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean deleteUser(int id) throws SQLException { // Khắc phục lỗi: deleteUser(int)
        boolean rowDeleted;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    // ... (insertUser, getUserById, isEmailExists)
    public Optional<Integer> insertUser(User user) throws SQLException {
        int generatedId = -1;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setInt(4, user.getRoleId());
            preparedStatement.setBoolean(5, user.isActive());

            if (preparedStatement.executeUpdate() > 0) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        return Optional.of(generatedId);
                    }
                }
            }
        }
        return Optional.empty();
    }

    public Optional<User> getUserById(int id) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {

            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getInt("role_id"),
                            rs.getBoolean("is_active"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        }
        return Optional.empty();
    }

    public boolean isEmailExists(String email) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_EMAIL_EXISTS)) {

            preparedStatement.setString(1, email);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}