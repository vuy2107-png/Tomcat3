package dao;

import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProductDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/shopclothes";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

    private static final String INSERT_PRODUCT = "INSERT INTO products(name, price, quantity, description) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM products";
    private static final String SELECT_BY_ID = "SELECT * FROM products WHERE id = ?";
    private static final String UPDATE_PRODUCT = "UPDATE products SET name=?, price=?, quantity=?, description=? WHERE id=?";
    private static final String DELETE_PRODUCT = "DELETE FROM products WHERE id=?";

    public ProductDAO() {}

    protected Connection getConnection() throws Exception {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Override
    public void addProduct(Product p) throws Exception {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(INSERT_PRODUCT);
        ps.setString(1, p.getName());
        ps.setDouble(2, p.getPrice());
        ps.setInt(3, p.getQuantity());
        ps.setString(4, p.getDescription());
        ps.executeUpdate();
    }

    @Override
    public List<Product> getAllProducts() throws Exception {
        List<Product> list = new ArrayList<>();

        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getString("description")
            );
            list.add(p);
        }

        return list;
    }

    @Override
    public Product getProductById(int id) throws Exception {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getString("description")
            );
        }
        return null;
    }

    @Override
    public void updateProduct(Product p) throws Exception {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(UPDATE_PRODUCT);
        ps.setString(1, p.getName());
        ps.setDouble(2, p.getPrice());
        ps.setInt(3, p.getQuantity());
        ps.setString(4, p.getDescription());
        ps.setInt(5, p.getId());
        ps.executeUpdate();
    }

    @Override
    public void deleteProduct(int id) throws Exception {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(DELETE_PRODUCT);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
