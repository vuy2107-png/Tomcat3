package dao;

import model.Product;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IProductDAO {
    List<Product> getAllProducts() throws SQLException;
    void insertProduct(Product product) throws SQLException;
    boolean deleteProduct(int id) throws SQLException;
    Optional<Product> getProductById(int id) throws SQLException;
    boolean updateProduct(Product product) throws SQLException;
}