package com.hfad.appgodsproject.database.api.schema;

public interface IReceiptSchema {

    String RECEIPT_TABLE = "receipts";
    String COLUMN_ID = "_id";
    String COLUMN_PURCHASE_DATE = "purchase_date";
    String COLUMN_TOTAL_PRICE = "total_price";
    String COLUMN_SUB_TOTAL = "sub_total";
    String COLUMN_LOCATION_ID = "location_id";

    String RECEIPT_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + RECEIPT_TABLE
            + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_PURCHASE_DATE + " INTEGER, "
            + COLUMN_SUB_TOTAL + " INTEGER NOT NULL, "
            + COLUMN_TOTAL_PRICE + " INTEGER NOT NULL, "
            + COLUMN_LOCATION_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + COLUMN_LOCATION_ID + ") REFERENCES "
            + ILocationSchema.LOCATION_TABLE + "(" + ILocationSchema.COLUMN_ID + ")"
            + ")";

    String RECEIPT_DROP_TABLE = "DROP TABLE IF EXISTS " + RECEIPT_TABLE;

    String [] RECEIPT_COLUMNS = new String[] {COLUMN_ID, COLUMN_PURCHASE_DATE, COLUMN_SUB_TOTAL, COLUMN_TOTAL_PRICE, COLUMN_LOCATION_ID};
}
