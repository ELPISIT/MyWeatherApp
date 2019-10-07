package com.umeshgunasekara.myweatherapp.service;

import com.umeshgunasekara.myweatherapp.entity.Location;
import com.umeshgunasekara.myweatherapp.entity.Weather;

import java.util.Optional;

/**
 * Created by Umesh Gunasekara on 2019-10-06.
 */
public interface LocationService {

    /**
     * @apiNote Get All Locations.
     */
    public Iterable<Location> getAllLocations();

    /**
     * @param locationId as String Object.
     * @return Location Optional Object.
     * @apiNote Get Location By locationId.
     */
    public Optional<Location> findLocationById(String locationId);

    /**
     * @param location as Location Object.
     * @apiNote Add Location.
     */
    public void saveLocation(Location location);

    /**
     * @param locationId as String Object.
     * @apiNote Delete Location.
     */
    public void deleteLocationById(String locationId);

    /**
     * @param locationName as String Object.
     * @return Location Optional Object.
     * @apiNote Get Location By locationName.
     */
    public Optional<Location> findLocationByLocationName(String locationName);

    /**
     * @param locations as Iterable Object.
     * @apiNote Add All Locations.
     */
    public void saveLocations(Iterable<Location> locations);

}
