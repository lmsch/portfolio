package com.hfad.appgodsproject.database.api.schema;

public interface IItemCategoryMapSchema {

    String ITEM_CATEGORY_MAP_TABLE = "item_category_mapping";
    String COLUMN_ITEM_ID = "item_id";
    String COLUMN_CATEGORY_ID= "category_id";

    String ITEM_CATEGORY_MAP_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + ITEM_CATEGORY_MAP_TABLE
            + " ("
            + COLUMN_ITEM_ID + " INTEGER NOT NULL, "
            + COLUMN_CATEGORY_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + COLUMN_ITEM_ID + ") REFERENCES "
            + IItemSchema.ITEM_TABLE + "(" + IItemSchema.COLUMN_ID + "), "
            + "FOREIGN KEY (" + COLUMN_CATEGORY_ID + ") REFERENCES "
            + ICategorySchema.CATEGORY_TABLE + "(" + ICategorySchema.COLUMN_ID + ")"
            + ")";

    String ITEM_CATEGORY_MAP_DROP_TABLE = "DROP TABLE IF EXISTS " + ITEM_CATEGORY_MAP_TABLE;

    String [] ITEM_CATEGORY_MAP_COLUMNS = new String[] {COLUMN_ITEM_ID, COLUMN_CATEGORY_ID};
}
