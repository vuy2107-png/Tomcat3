package service;

import model.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAll();

    Product getById(int id);

    boolean add(Product p);

    boolean update(Product p);

    boolean delete(int id);

    List<Product> search(String keyword);
}