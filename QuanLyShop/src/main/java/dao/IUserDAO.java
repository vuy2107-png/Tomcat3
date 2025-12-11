package dao;

import model.User;
import java.util.List;

public interface IUserDAO {

    // CREATE
    void addUser(User user) throws Exception;

    // READ
    User getUserById(int id) throws Exception;
    User getUserByEmail(String email) throws Exception;
    List<User> getAllUsers() throws Exception;

    // UPDATE
    void updateUser(User user) throws Exception;

    // DELETE
    void deleteUser(int id) throws Exception;
}
