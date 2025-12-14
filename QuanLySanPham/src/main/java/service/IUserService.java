package service;

import model.User;

import java.util.List;

public interface IUserService {
    boolean register(User user);

    User login(String email, String password);

    List<User> getAll();

    boolean delete(int id);
}