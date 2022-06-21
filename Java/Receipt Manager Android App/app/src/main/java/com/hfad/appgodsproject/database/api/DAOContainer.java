package com.hfad.appgodsproject.database.api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hfad.appgodsproject.pojos.Category;
import com.hfad.appgodsproject.pojos.Item;
import com.hfad.appgodsproject.pojos.Location;
import com.hfad.appgodsproject.pojos.Receipt;

import java.util.ArrayList;
import java.util.List;

public class DAOContainer {

    private Context context;
    private AppGodsDbHelper dbHelper;
    private ReceiptDAO receiptDAO;
    private ItemDAO itemDAO;
    private CategoryDAO categoryDAO;
    private LocationDAO locationDAO;

    public DAOContainer(Context context) {
        this.context = context;
    }

    public Receipt getPopulatedReceipt(long receiptId) {
        Receipt receipt = receiptDAO.fetchReceiptById(receiptId);
        long locationId = receipt.getLocationId();
        Location location = locationDAO.fetchLocationById(locationId);
        receipt.setLocation(location);
        List<Item> itemList = itemDAO.fetchItemsByReceiptId(receipt.getId());
        receipt.setItemList(itemList);
        for (Item item : itemList) {
            List<Category> categoryList = categoryDAO.fetchCategoriesByItemId(item.getId());
            item.setCategoryList(categoryList);
        }
        return receipt;
    }

    public long addPopulatedReceipt(Receipt receipt) {
        long index = receiptDAO.addReceipt(receipt);
        List<Item> itemList = receipt.getItemList();
        if(itemList != null)
            itemDAO.addItems(itemList, index);
        return index;
    }

    public List<Item> getAllItems() {
        List<Item> itemList = itemDAO.fetchAllItems();
        for (Item item : itemList) {
            List<Category> categoryList = categoryDAO.fetchCategoriesByItemId(item.getId());
            item.setCategoryList(categoryList);
        }
        return itemList;
    }

    public List<Location> fetchAllLocationsForReceipts(List<Receipt> receiptList) {
        List<Location> locationList = new ArrayList<>();
        for(Receipt receipt : receiptList) {
            long locationId = receipt.getLocationId();
            Location location = locationDAO.fetchLocationById(locationId);
            locationList.add(location);
        }
        return locationList;
    }

    public void open() {
        dbHelper = new AppGodsDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        receiptDAO = new ReceiptDAO(db);
        itemDAO = new ItemDAO(db);
        categoryDAO = new CategoryDAO(db);
        locationDAO = new LocationDAO(db);
    }

    public void close() {
        dbHelper.close();
    }

    public ReceiptDAO getReceiptDAO() {
        return receiptDAO;
    }

    public ItemDAO getItemDAO() {
        return itemDAO;
    }

    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    public LocationDAO getLocationDAO() {
        return locationDAO;
    }
}
