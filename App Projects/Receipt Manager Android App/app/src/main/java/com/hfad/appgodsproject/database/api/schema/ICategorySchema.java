package com.hfad.appgodsproject.database.api.schema;

public interface ICategorySchema {

    String CATEGORY_TABLE = "categories";
    String COLUMN_ID = "_id";
    String COLUMN_DESCRIPTION = "description";

    String CATEGORY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + CATEGORY_TABLE
            + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_DESCRIPTION + " TEXT NOT NULL"
            + ")";

    String CATEGORY_DROP_TABLE = "DROP TABLE IF EXISTS " + CATEGORY_TABLE;

    String [] CATEGORY_COLUMNS = new String[] {COLUMN_ID, COLUMN_DESCRIPTION};
}
