package com.hfad.appgodsproject.database.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class ContentProvider {

    private SQLiteDatabase db;

    public ContentProvider(SQLiteDatabase db) {
        this.db = db;
    }

    public int delete(String tableName, String selection, String[] selectionArgs) {
        return db.delete(tableName, selection, selectionArgs);
    }

    public long insert(String tableName, ContentValues values) {
        return db.insert(tableName, null, values);
    }

    protected abstract <T> T cursorToEntity(Cursor cursor);

    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs) {
        final Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null,
                null, null);
        return cursor;
    }

    public Cursor query(String tableName, String[] columns, String selection,
                        String[] selectionArgs, String sortOrder) {
        return db.query(tableName, columns, selection, selectionArgs, null, null,
                sortOrder, null);
    }

    public int update(String tableName, ContentValues values, String selection, String[] selectionArgs) {
        return db.update(tableName, values, selection, selectionArgs);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return db.rawQuery(sql, selectionArgs);
    }
}

