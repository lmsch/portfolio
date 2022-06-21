package com.hfad.appgodsproject.database.api.schema;

import com.hfad.appgodsproject.pojos.Category;
import com.hfad.appgodsproject.pojos.Item;

import java.util.List;

public interface IItemDAO {
    public Item fetchItemById(long itemId);
    public List<Item> fetchItemsByReceiptId(long receiptId);
    public List<Item> fetchAllItems();
    public long addItem(Item item, long receiptId);
    public List<Long> addItems(List<Item> itemList, long receiptId);
    public long addItemCategoryMapping(long itemId, long categoryId);
    public List<Long> addItemCategoryMappings(List<Category> categoryList, long itemId);
}
