package entity;

import java.io.Serializable;
import java.util.Comparator;

public class Product implements Comparable<Product>, Serializable {
    private String productId;
    private String name;
    private long price;
    private String categoryId;

    public Product() {
    }

    public Product(String productId, String name, long price, String categoryId) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return productId + "," + name + "," + price + "," + categoryId;
    }

    @Override
    public int compareTo(Product o) {
        return 0;
    }
}
