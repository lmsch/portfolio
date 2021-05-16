package com.hfad.appgodsproject.database.api.schema;

public interface IItemSchema {

    String ITEM_TABLE = "items";
    String COLUMN_ID = "_id";
    String COLUMN_DESCRIPTION = "description";
    String COLUMN_QUANTITY = "quantity";
    String COLUMN_PRICE = "price";
    String COLUMN_RECEIPT_ID = "receipt_id";

    String ITEM_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + ITEM_TABLE
            + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_DESCRIPTION + " TEXT NOT NULL, "
            + COLUMN_QUANTITY + " INTEGER NOT NULL, "
            + COLUMN_PRICE + " INTEGER NOT NULL, "
            + COLUMN_RECEIPT_ID  + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + COLUMN_RECEIPT_ID + ") REFERENCES "
            + IReceiptSchema.RECEIPT_TABLE + "(" + IReceiptSchema.COLUMN_ID + ")"
            + ")";

    String ITEM_DROP_TABLE = "DROP TABLE IF EXISTS " + ITEM_TABLE;

    String [] ITEM_COLUMNS = new String[] {COLUMN_ID, COLUMN_DESCRIPTION, COLUMN_QUANTITY,
            COLUMN_PRICE, COLUMN_RECEIPT_ID};
}
