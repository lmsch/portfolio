package com.hfad.appgodsproject.database.api.schema;

public interface ILocationSchema {

    String LOCATION_TABLE = "locations";
    String COLUMN_ID = "_id";
    String COLUMN_DESCRIPTION = "description";

    String LOCATION_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + LOCATION_TABLE
            + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_DESCRIPTION + " TEXT NOT NULL"
            + ")";

    String LOCATION_DROP_TABLE = "DROP TABLE IF EXISTS " + LOCATION_TABLE;

    String [] LOCATION_COLUMNS = new String[] {COLUMN_ID, COLUMN_DESCRIPTION};
}
