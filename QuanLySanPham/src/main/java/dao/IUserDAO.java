package dao;

import model.User;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IUserDAO {
    // Phương thức đã có cho đăng nhập
    Optional<User> getUserByEmailAndPassword(String email, String password) throws SQLException;

    // CRUD Methods
    List<User> getAllUsers() throws SQLException;
    void insertUser(User user) throws SQLException;
    void deleteUser(int id) throws SQLException;
    Optional<User> getUserById(int id) throws SQLException;
    boolean updateUser(User user) throws SQLException;
}