package com.hfad.appgodsproject.database.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hfad.appgodsproject.database.api.schema.ICategoryDAO;
import com.hfad.appgodsproject.database.api.schema.ICategorySchema;
import com.hfad.appgodsproject.database.api.schema.IItemCategoryMapSchema;
import com.hfad.appgodsproject.pojos.Category;
import com.hfad.appgodsproject.pojos.Receipt;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO extends ContentProvider implements ICategorySchema, ICategoryDAO {

    private final String[] BASE_CATEGORIES = {"Food", "Clothing", "Entertainment", "Bills"};

    public final String QUERY_CATEGORIES_FROM_ITEM = "SELECT *"
            + " FROM " + IItemCategoryMapSchema.ITEM_CATEGORY_MAP_TABLE
            + " JOIN " + CATEGORY_TABLE + " ON "
            + IItemCategoryMapSchema.COLUMN_CATEGORY_ID + " = " + COLUMN_ID
            + " WHERE " + IItemCategoryMapSchema.COLUMN_ITEM_ID + " = ?";

    public CategoryDAO(SQLiteDatabase db) {
        super(db);
    }

    @Override
    protected Category cursorToEntity(Cursor cursor) {
        int index;
        Category category = new Category();
        index = cursor.getColumnIndexOrThrow(COLUMN_ID);
        category.setId(cursor.getLong(index));
        index = cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION);
        category.setDescription(cursor.getString(index));
        return category;
    }

    @Override
    public Category fetchCategoryById(long categoryId) {
        Cursor cursor = query(CATEGORY_TABLE, CATEGORY_COLUMNS,
                COLUMN_ID + " = " + categoryId, null);
        cursor.moveToFirst();
        return cursorToEntity(cursor);
    }

    @Override
    public long addCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, category.getDescription());
        return insert(CATEGORY_TABLE, values);
    }

    @Override
    public List<Category> fetchCategoriesByItemId(long itemId) {
        List<Category> categoryList = new ArrayList<>();
        Cursor cursor = rawQuery(QUERY_CATEGORIES_FROM_ITEM, new String[] {String.valueOf(itemId)});
        while(cursor.moveToNext()) {
            Category category = cursorToEntity(cursor);
            categoryList.add(category);
        }
        return categoryList;
    }

    @Override
    public List<Category> fetchAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        Cursor cursor = query(CATEGORY_TABLE, CATEGORY_COLUMNS, null, null);
        while(cursor.moveToNext()) {
            categoryList.add(cursorToEntity(cursor));
        }
        return categoryList;
    }

    @Override
    public List<Long> addCategories(List<Category> categoryList) {
        List<Long> categoryIds = new ArrayList<>();
        for(Category category : categoryList) {
            long index = addCategory(category);
            categoryIds.add(index);
        }
        return categoryIds;
    }

    protected void addBaseCategories() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(BASE_CATEGORIES[0]));
        categoryList.add(new Category(BASE_CATEGORIES[1]));
        categoryList.add(new Category(BASE_CATEGORIES[2]));
        categoryList.add(new Category(BASE_CATEGORIES[3]));
        addCategories(categoryList);
    }
}
