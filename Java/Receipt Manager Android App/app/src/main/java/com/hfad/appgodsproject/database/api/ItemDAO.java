package com.hfad.appgodsproject.database.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hfad.appgodsproject.database.api.schema.IItemCategoryMapSchema;
import com.hfad.appgodsproject.database.api.schema.IItemDAO;
import com.hfad.appgodsproject.database.api.schema.IItemSchema;
import com.hfad.appgodsproject.pojos.Category;
import com.hfad.appgodsproject.pojos.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO extends ContentProvider implements IItemDAO, IItemSchema {

    public ItemDAO(SQLiteDatabase db) {
        super(db);
    }

    @Override
    protected Item cursorToEntity(Cursor cursor) {
        int index;
        Item item = new Item();
        index = cursor.getColumnIndexOrThrow(COLUMN_ID);
        item.setId(cursor.getLong(index));
        index = cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION);
        item.setDescription(cursor.getString(index));
        index = cursor.getColumnIndexOrThrow(COLUMN_QUANTITY);
        item.setQuantity(cursor.getInt(index));
        index = cursor.getColumnIndexOrThrow(COLUMN_PRICE);
        item.setPrice(cursor.getInt(index));
        index = cursor.getColumnIndexOrThrow(COLUMN_RECEIPT_ID);
        item.setReceiptId(cursor.getLong(index));
        return item;
    }

    @Override
    public Item fetchItemById(long itemId) {
        Cursor cursor = query(ITEM_TABLE, ITEM_COLUMNS,
                COLUMN_ID + " = " + itemId, null);
        cursor.moveToFirst();
        return cursorToEntity(cursor);
    }

    @Override
    public List<Item> fetchItemsByReceiptId(long receiptId) {
        List<Item> itemList = new ArrayList<>();
        Cursor cursor = query(ITEM_TABLE, ITEM_COLUMNS,
                COLUMN_RECEIPT_ID + " = " + receiptId, null);
        while(cursor.moveToNext()) {
            Item item = cursorToEntity(cursor);
            itemList.add(item);
        }
        return itemList;
    }

    @Override
    public List<Item> fetchAllItems() {
        List<Item> itemList = new ArrayList<>();
        Cursor cursor = query(ITEM_TABLE, ITEM_COLUMNS, null, null, null);
        while (cursor.moveToNext()) {
            Item item = cursorToEntity(cursor);
            itemList.add(item);
        }
        return itemList;
    }

    @Override
    public long addItem(Item item, long receiptId) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, item.getDescription());
        values.put(COLUMN_PRICE, item.getPrice());
        values.put(COLUMN_QUANTITY, item.getQuantity());
        values.put(COLUMN_RECEIPT_ID, receiptId);
        long index = insert(ITEM_TABLE, values);
        List<Category> categoryList = item.getCategoryList();
        if (categoryList != null)
            addItemCategoryMappings(categoryList, index);
        return index;
    }

    @Override
    public List<Long> addItemCategoryMappings(List<Category> categoryList, long itemId) {
        List<Long> indexList = new ArrayList<>();
        for(Category category : categoryList) {
            long index = addItemCategoryMapping(itemId, category.getId());
            indexList.add(index);
        }
        return indexList;
    }

    @Override
    public long addItemCategoryMapping(long itemId, long categoryId) {
        ContentValues values = new ContentValues();
        values.put(IItemCategoryMapSchema.COLUMN_ITEM_ID, itemId);
        values.put(IItemCategoryMapSchema.COLUMN_CATEGORY_ID, categoryId);
        return insert(IItemCategoryMapSchema.ITEM_CATEGORY_MAP_TABLE, values);
    }

    @Override
    public List<Long> addItems(List<Item> itemList, long receiptId) {
        List<Long> itemIds = new ArrayList<>();
        for(Item item : itemList) {
            long index = addItem(item, receiptId);
            itemIds.add(index);
        }
        return itemIds;
    }
}
