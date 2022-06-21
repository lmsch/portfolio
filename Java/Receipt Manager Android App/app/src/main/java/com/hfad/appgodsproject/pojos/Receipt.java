package com.hfad.appgodsproject.pojos;

import java.util.Date;
import java.util.List;

public class Receipt {

    private Date purchaseDate;
    private int totalPrice, subTotal;
    private long id, locationId;
    private List<Item> itemList;
    private Location location;

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public Receipt(Date purchaseDate, int subTotal, int totalPrice) {
        this.purchaseDate = purchaseDate;
        this.totalPrice = totalPrice;
        this.subTotal = subTotal;
    }

    public Receipt() {}

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
