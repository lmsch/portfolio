package com.hfad.appgodsproject.pojos;

import java.util.List;

public class Item {

    private String description;
    private long id, receiptId;
    private List<Category> categoryList;
    private int quantity, price;

    public Item(String description, int quantity, int price) {
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public Item() {}

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(long receiptId) {
        this.receiptId = receiptId;
    }
}
