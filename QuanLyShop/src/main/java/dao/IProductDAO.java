package dao;

import model.Product;
import java.util.List;

public interface IProductDAO {
    void addProduct(Product product) throws Exception;

    List<Product> getAllProducts() throws Exception;

    Product getProductById(int id) throws Exception;

    void updateProduct(Product product) throws Exception;

    void deleteProduct(int id) throws Exception;
}
