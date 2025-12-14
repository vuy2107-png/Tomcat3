package dao;

import model.Product;
import java.util.List;

public interface IProductDAO {

    void addProduct(Product p);

    List<Product> getAllProducts();

    Product getProductById(int id);

    void updateProduct(Product p);

    void deleteProduct(int id);
}
