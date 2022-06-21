package com.hfad.appgodsproject.database.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hfad.appgodsproject.database.api.schema.ILocationDAO;
import com.hfad.appgodsproject.database.api.schema.ILocationSchema;
import com.hfad.appgodsproject.pojos.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationDAO extends ContentProvider implements ILocationSchema, ILocationDAO {

    public LocationDAO(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public Location fetchLocationById(long locationId) {
        Cursor cursor = query(LOCATION_TABLE, LOCATION_COLUMNS,
                COLUMN_ID + " = " + locationId, null);
        cursor.moveToFirst();
        return cursorToEntity(cursor);
    }

    @Override
    public List<Location> fetchAllLocations() {
        List<Location> locationList = new ArrayList<>();
        Cursor cursor = query(LOCATION_TABLE, LOCATION_COLUMNS, null, null);
        while(cursor.moveToNext()) {
            locationList.add(cursorToEntity(cursor));
        }
        return locationList;
    }

    @Override
    public long addLocation(Location location) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, location.getDescription());
        return insert(LOCATION_TABLE, values);
    }

    @Override
    public List<Long> addLocations(List<Location> locationList) {
        List<Long> locationIds = new ArrayList<>();
        for(Location location : locationList) {
            long index = addLocation(location);
            locationIds.add(index);
        }
        return locationIds;
    }

    @Override
    protected Location cursorToEntity(Cursor cursor) {
        int index;
        Location location = new Location();
        index = cursor.getColumnIndexOrThrow(COLUMN_ID);
        location.setId(cursor.getLong(index));
        index = cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION);
        location.setDescription(cursor.getString(index));
        return location;
    }
}
