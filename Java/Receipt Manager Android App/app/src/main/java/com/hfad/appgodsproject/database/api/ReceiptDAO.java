package com.hfad.appgodsproject.database.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hfad.appgodsproject.database.api.schema.IReceiptDAO;
import com.hfad.appgodsproject.database.api.schema.IReceiptSchema;
import com.hfad.appgodsproject.pojos.Receipt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReceiptDAO extends ContentProvider implements IReceiptDAO, IReceiptSchema {

    public static final String LAST_WEEK = "-6 days";
    public static final String LAST_MONTH = "start of month";
    public static final String LAST_YEAR = "start of year";
    public static final String LAST_TWO_WEEKS = "-13 days";

    public ReceiptDAO(SQLiteDatabase db) {
        super(db);
    }

    @Override
    protected Receipt cursorToEntity(Cursor cursor) {
        int index;
        Receipt receipt = new Receipt();
        index = cursor.getColumnIndexOrThrow(COLUMN_ID);
        receipt.setId(cursor.getLong(index));
        index = cursor.getColumnIndexOrThrow(COLUMN_PURCHASE_DATE);
        if(!cursor.isNull(index)) {
            long unixMillis = convertFromUnixTime(cursor.getLong(index));
            Date purchaseDate = new Date(unixMillis);
            receipt.setPurchaseDate(purchaseDate);
        }
        index = cursor.getColumnIndexOrThrow(COLUMN_SUB_TOTAL);
        receipt.setSubTotal(cursor.getInt(index));
        index = cursor.getColumnIndexOrThrow(COLUMN_TOTAL_PRICE);
        receipt.setTotalPrice(cursor.getInt(index));
        index = cursor.getColumnIndexOrThrow(COLUMN_LOCATION_ID);
        receipt.setLocationId(cursor.getLong(index));
        return receipt;
    }

    @Override
    public Receipt fetchReceiptById(long receiptId) {
        Cursor cursor = query(RECEIPT_TABLE, RECEIPT_COLUMNS,
                COLUMN_ID + " = " + receiptId, null);
        cursor.moveToFirst();
        return cursorToEntity(cursor);
    }

    public List<Receipt> fetchAllReceipts() {
        Cursor cursor = query(RECEIPT_TABLE, RECEIPT_COLUMNS, null, null);
        List<Receipt> allReceipts = new ArrayList<>();
        while (cursor.moveToNext()) {
            allReceipts.add(cursorToEntity(cursor));
        }
        return allReceipts;
    }

    public List<Receipt> fetchReceiptsInDateRange(String range) {
        if(range == null)
            return fetchAllReceipts();
        Cursor cursor = query(RECEIPT_TABLE, RECEIPT_COLUMNS, "datetime(" + COLUMN_PURCHASE_DATE + ", 'unixepoch') BETWEEN " +
                "datetime('now', '" + range + "') AND datetime('now', 'localtime')", null, COLUMN_PURCHASE_DATE + " DESC" );
        List<Receipt> allReceipts = new ArrayList<>();
        while (cursor.moveToNext()) {
            allReceipts.add(cursorToEntity(cursor));
        }
        return allReceipts;
    }

    @Override
    public long addReceipt(Receipt receipt) {
        ContentValues values = new ContentValues();
        Date purchaseDate = receipt.getPurchaseDate();
        if(purchaseDate != null) {
            long unixSeconds = convertToUnixTime(receipt.getPurchaseDate().getTime());
            values.put(COLUMN_PURCHASE_DATE, unixSeconds);
        }
        values.put(COLUMN_TOTAL_PRICE, receipt.getTotalPrice());
        values.put(COLUMN_SUB_TOTAL,  receipt.getSubTotal());
        values.put(COLUMN_LOCATION_ID, receipt.getLocationId());
        return insert(RECEIPT_TABLE, values);
    }

    @Override
    public List<Long> addReceipts(List<Receipt> receiptList) {
        List<Long> receiptIds = new ArrayList<>();
        for(Receipt receipt : receiptList) {
            long index = addReceipt(receipt);
            receiptIds.add(index);
        }
        return receiptIds;
    }

    private Long convertToUnixTime(long time) {
        return time/1000;
    }

    private Long convertFromUnixTime(long time) {
        return time*1000;
    }
}

