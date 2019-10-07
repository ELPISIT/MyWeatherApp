package com.umeshgunasekara.myweatherapp.service.Impl;

import com.umeshgunasekara.myweatherapp.dao.LocationDao;
import com.umeshgunasekara.myweatherapp.entity.Location;
import com.umeshgunasekara.myweatherapp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Umesh Gunasekara on 2019-10-06.
 */
@Service
public class LocationServiceImpl  implements LocationService{

    @Autowired
    private LocationDao locationDao;

    @Override
    public Iterable<Location> getAllLocations() {
        return locationDao.findAll();
    }

    @Override
    public Optional<Location> findLocationById(String locationId) {
        return locationDao.findById(locationId);
    }

    @Override
    public void saveLocation(Location location) {
        locationDao.save(location);
    }

    @Override
    public void deleteLocationById(String locationId) {
        locationDao.deleteById(locationId);
    }

    @Override
    public Optional<Location> findLocationByLocationName(String locationName) {
        return locationDao.findLocationByLocationName(locationName);
    }

    @Override
    public void saveLocations(Iterable<Location> locations) {
        locationDao.insert(locations);
    }
}
