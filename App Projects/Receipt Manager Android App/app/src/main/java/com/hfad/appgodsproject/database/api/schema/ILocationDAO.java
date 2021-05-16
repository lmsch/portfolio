package com.hfad.appgodsproject.database.api.schema;

import com.hfad.appgodsproject.pojos.Location;

import java.util.List;

public interface ILocationDAO  {
    public Location fetchLocationById(long locationId);
    public List<Location> fetchAllLocations();
    public long addLocation(Location location);
    public List<Long> addLocations(List<Location> locationList);
}
