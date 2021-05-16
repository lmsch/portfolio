package com.hfad.appgodsproject.database.api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hfad.appgodsproject.database.api.schema.ICategorySchema;
import com.hfad.appgodsproject.database.api.schema.IItemCategoryMapSchema;
import com.hfad.appgodsproject.database.api.schema.IItemDAO;
import com.hfad.appgodsproject.database.api.schema.IItemSchema;
import com.hfad.appgodsproject.database.api.schema.ILocationSchema;
import com.hfad.appgodsproject.database.api.schema.IReceiptSchema;

public class AppGodsDbHelper extends SQLiteOpenHelper implements IItemSchema, IReceiptSchema,
        ICategorySchema, IItemCategoryMapSchema, ILocationSchema {

    public static final int DATABASE_VERSION = 12;
    public static final String DATABASE_NAME = "AppGods.db";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RECEIPT_CREATE_TABLE);
        db.execSQL(ITEM_CREATE_TABLE);
        db.execSQL(CATEGORY_CREATE_TABLE);
        db.execSQL(ITEM_CATEGORY_MAP_CREATE_TABLE);
        db.execSQL(LOCATION_CREATE_TABLE);

        CategoryDAO cat = new CategoryDAO(db);
        cat.addBaseCategories();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 12) {
            db.execSQL(RECEIPT_DROP_TABLE);
            db.execSQL(ITEM_DROP_TABLE);
            db.execSQL(CATEGORY_DROP_TABLE);
            db.execSQL(ITEM_CATEGORY_MAP_DROP_TABLE);
            db.execSQL(LOCATION_DROP_TABLE);
            onCreate(db);
        }
    }

    public AppGodsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
