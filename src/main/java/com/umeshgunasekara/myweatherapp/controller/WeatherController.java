package com.umeshgunasekara.myweatherapp.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umeshgunasekara.myweatherapp.entity.DarkSkyWeather;
import com.umeshgunasekara.myweatherapp.entity.Location;
import com.umeshgunasekara.myweatherapp.resources.DarkSkyUrl;
import com.umeshgunasekara.myweatherapp.resources.FilterWeatherApi;
import com.umeshgunasekara.myweatherapp.service.DarkSkyWeatherService;
import com.umeshgunasekara.myweatherapp.service.LocationService;
import com.umeshgunasekara.myweatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
    private DarkSkyUrl darkSkyUrl;

    @RequestMapping("/hi2")
    public String hi()throws JsonParseException, JsonMappingException, IOException {
//        Optional<Location> location=locationService.findLocationByLocationName("Austin,TX");
        List<Location> locations= (List<Location>) locationService.getAllLocations();
        List<DarkSkyWeather> darkSkyWeathers=new ArrayList<DarkSkyWeather>();
        for(Location loc:locations){
            UriComponents uriComponents = UriComponentsBuilder
                    .newInstance()
                    .scheme("https")
                    .host(darkSkyUrl.getUrl())
                    .path("/{apikey}/{lat},{lon}")
                    .buildAndExpand(darkSkyUrl.getApiKey(),loc.getLocationLatitude(),loc.getLocationLongitude());

            String uri = uriComponents.toUriString();

            ResponseEntity<String> resp= restTemplate.exchange(uri, HttpMethod.GET, null, String.class);

            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            DarkSkyWeather weather = mapper.readValue(resp.getBody(), DarkSkyWeather.class);
            weather.getHourlyData().forEach(item->{
                item.set_id(loc.get_id()+item.getTime());
                item.setLocationId(loc.get_id());
            });
            darkSkyWeathers.add(weather);
        }

        darkSkyWeathers.forEach(item->{
            item.getHourlyData().forEach(itemw->{
                weatherService.saveWeather(itemw);
            });
        });
        return darkSkyWeathers.size()+"";
    }

    @PostMapping("/hi3")
    public String hi3(@RequestBody String s)throws JsonParseException, JsonMappingException, IOException {
        Optional<Location> location=locationService.findLocationByLocationName("Austin,TX");
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DarkSkyWeather darkSkyWeather=mapper.readValue(s,DarkSkyWeather.class);
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
