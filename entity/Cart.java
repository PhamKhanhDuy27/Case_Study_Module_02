package entity;

import java.util.List;

public class Cart {
    private String userId;
    private long total;
    private List<CartLine> cartLinesUser;

    public Cart() {
    }

    public Cart(String userId, long total, List<CartLine> cartLinesUser) {
        this.userId = userId;
        this.total = total;
        this.cartLinesUser = cartLinesUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<CartLine> getCartLinesUser() {
        return cartLinesUser;
    }

    public void setCartLinesUser(List<CartLine> cartLinesUser) {
        this.cartLinesUser = cartLinesUser;
    }

    @Override
    public String toString() {
        return userId + "," + total + "," + cartLinesUser;
    }
}
