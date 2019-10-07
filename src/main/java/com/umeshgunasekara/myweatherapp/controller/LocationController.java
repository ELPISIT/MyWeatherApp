package com.umeshgunasekara.myweatherapp.controller;

import com.umeshgunasekara.myweatherapp.entity.Location;
import com.umeshgunasekara.myweatherapp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Umesh Gunasekara on 2019-10-06.
 */
@RestController
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/addlocation")
    public String saveLocation(@RequestBody Location location){
        locationService.saveLocation(location);
        return "Added location with id : "+location.getLocationName();
    }

    @PostMapping("/addlocations")
    public String saveLocation(@RequestBody List<Location> locations){
        locationService.saveLocations(locations);
        return "Added location count : "+locations.size();
    }
}
