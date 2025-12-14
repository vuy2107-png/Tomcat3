package model;

// model/Product.java
public class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String description;
    private String imageUrl;
    private int categoryId; // THÊM TRƯỜNG MỚI

    // Constructor ĐẦY ĐỦ
    public Product(int id, String name, double price, int quantity, String description, String imageUrl, int categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }

    // Constructor cho việc INSERT (id=0)
    public Product(String name, double price, int quantity, String description, String imageUrl, int categoryId) {
        this(0, name, price, quantity, description, imageUrl, categoryId);
    }

    // Constructor cũ (giữ lại nếu cần cho mục đích tương thích)
    public Product(int id, String name, double price, int quantity, String description, String imageUrl) {
        this(id, name, price, quantity, description, imageUrl, 0); // categoryId mặc định là 0 hoặc null nếu dùng Integer
    }

    // Getters and Setters (ĐẢM BẢO CÓ GETTER/SETTER CHO categoryId)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getCategoryId() { // GETTER MỚI
        return categoryId;
    }

    public void setCategoryId(int categoryId) { // SETTER MỚI
        this.categoryId = categoryId;
    }
}