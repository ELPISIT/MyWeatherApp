package com.umeshgunasekara.myweatherapp.controller;

import com.umeshgunasekara.myweatherapp.entity.Location;
import com.umeshgunasekara.myweatherapp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Umesh Gunasekara on 2019-10-06.
 */
@RestController
public class LocationController {

    @Autowired
    private LocationService locationService;

    @RequestMapping(method= RequestMethod.POST, value="/addlocation")
    public String saveLocation(@RequestBody Location location){
        locationService.saveLocation(location);
        return "Added location with id : "+location.getLocationName();
    }

   @RequestMapping(method= RequestMethod.POST, value="/addlocations")
    public String saveLocations(@RequestBody List<Location> locations){
        locationService.saveLocations(locations);
        return "Added location count : "+locations.size();
    }
}
