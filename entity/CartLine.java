package entity;

import java.io.Serializable;

public class CartLine implements Serializable {
    private String userId;
    private Product product;
    private int quantity;
    private long subtotal;

    public CartLine() {
    }

    public CartLine(String userId, Product product, int quantity, long subtotal) {
        this.userId = userId;
        this.product = product;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(long subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return userId + "," + product + "," + quantity + "," + subtotal;
    }
}
