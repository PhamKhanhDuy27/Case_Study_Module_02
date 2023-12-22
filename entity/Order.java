package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private String userId;
    private long total;
    private String shippingAddress;
    private Date orderDate;
    private String status;
    private List<CartLine> orderLineUser;

    public Order() {
    }

    public Order(String userId, long total, String shippingAddress, Date orderDate, String status, List<CartLine> orderLineUser) {
        this.userId = userId;
        this.total = total;
        this.shippingAddress = shippingAddress;
        this.orderDate = orderDate;
        this.status = status;
        this.orderLineUser = orderLineUser;
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

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CartLine> getOrderLineUser() {
        return orderLineUser;
    }

    public void setOrderLines(List<CartLine> orderLineUser) {
        this.orderLineUser = orderLineUser;
    }

    @Override
    public String toString() {
        return userId + "," + total + "," + shippingAddress + "," + orderDate + "," + status + "," + orderLineUser;
    }
}
