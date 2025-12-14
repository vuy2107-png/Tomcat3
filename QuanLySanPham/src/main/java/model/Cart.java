package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    // Thêm mục vào giỏ hàng
    public void addItem(CartItem item) {
        // Logic: kiểm tra xem sản phẩm đã có trong giỏ chưa. Nếu có thì tăng số lượng.
        for (CartItem existingItem : items) {
            if (existingItem.getProduct().getId() == item.getProduct().getId()) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    // Tổng tiền của cả giỏ hàng (Khắc phục lỗi getTotalAmount)
    public double getTotalAmount() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubTotal();
        }
        return total;
    }

    // Getters and Setters (Khắc phục lỗi getItems)
    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}