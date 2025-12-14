package model;

public class CartItem {
    private Product product; // Cần import model.Product
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Tổng tiền của mục này
    public double getSubTotal() {
        return product.getPrice() * quantity;
    }

    // Getters and Setters (đảm bảo có)
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}