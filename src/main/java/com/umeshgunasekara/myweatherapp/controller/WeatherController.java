package com.umeshgunasekara.myweatherapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umeshgunasekara.myweatherapp.entity.DarkSkyWeather;
import com.umeshgunasekara.myweatherapp.entity.Location;
import com.umeshgunasekara.myweatherapp.resources.DarkSkyUrl;
import com.umeshgunasekara.myweatherapp.service.DarkSkyWeatherService;
import com.umeshgunasekara.myweatherapp.service.LocationService;
import com.umeshgunasekara.myweatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;


@ComponentScan("com.umeshgunasekara.myweatherapp.config")
@RestController
public class WeatherController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private DarkSkyWeatherService darkSkyWeatherService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private DarkSkyUrl darkSkyUrl;

    @RequestMapping(method= RequestMethod.GET, value="/hi2")
    public String hi(){

        return "Hello";
    }

    @RequestMapping(method= RequestMethod.POST, value="/hi3")
    public String hi3(@RequestBody String s) throws IOException {
        Optional<Location> location=locationService.findLocationByLocationName("Austin,TX");
        DarkSkyWeather darkSkyWeather=objectMapper.readValue(s,DarkSkyWeather.class);
        darkSkyWeather.getHourlyData().forEach(item->{
            item.setLocationId(location.get().get_id());
        });

        darkSkyWeather.getHourlyData().forEach(item->{
            weatherService.saveWeather(item);
        });
//        weatherService.saveWeatherForcast(darkSkyWeather.getHourlyData());
        return darkSkyWeather.getTimezone();
    }
}
