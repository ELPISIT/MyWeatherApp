package com.umeshgunasekara.myweatherapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umeshgunasekara.myweatherapp.entity.DarkSkyWeather;
import com.umeshgunasekara.myweatherapp.entity.Location;
import com.umeshgunasekara.myweatherapp.entity.Weather;
import com.umeshgunasekara.myweatherapp.resources.DarkSkyUrl;
import com.umeshgunasekara.myweatherapp.service.DarkSkyWeatherService;
import com.umeshgunasekara.myweatherapp.service.LocationService;
import com.umeshgunasekara.myweatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@ComponentScan("com.umeshgunasekara.myweatherapp.config")
@Controller
public class WeatherAppController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private DarkSkyWeatherService darkSkyWeatherService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private DarkSkyUrl darkSkyUrl;

    @RequestMapping(value = "/weather",method= RequestMethod.POST )
    public String homePage(Model model, @RequestParam String city) throws IOException{
        List<Location> locationList= (List<Location>) locationService.getAllLocations();
        List<DarkSkyWeather> darkSkyWeathers=new ArrayList<DarkSkyWeather>();

        Map<String, ZoneId> timezones=new HashMap<String, ZoneId>();
        String pattern = "EEEEE MMMMM yyyy";
        String pattern2 = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);

        for(Location location:locationList){
            timezones.put(location.getLocationName(),ZoneId.of(location.getTimezone()));
            System.out.println(location.getLocationName()+" , "+location.getTimezone());
            Timestamp timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of(location.getTimezone())).withMinute(0).withSecond(0).toLocalDateTime());
            String timestampstr=(timestamp.getTime()/1000L)+"";
            Optional<Weather> weather=weatherService.findWeatherBy_idAndStatus(location.get_id()+timestampstr, "Original");
            System.out.println(location.getLocationName()+" , "+weather.isPresent());
            if(!weather.isPresent()){
                UriComponents uriComponents = UriComponentsBuilder
                        .newInstance()
                        .scheme("https")
                        .host(darkSkyUrl.getUrl())
                        .path("/{apikey}/{lat},{lon}")
                        .buildAndExpand(darkSkyUrl.getApiKey(),location.getLocationLatitude(),location.getLocationLongitude());

                String uri = uriComponents.toUriString();

                ResponseEntity<String> resp= restTemplate.exchange(uri, HttpMethod.GET, null, String.class);

//            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                DarkSkyWeather darkSkyWeather = objectMapper.readValue(resp.getBody(), DarkSkyWeather.class);

                Weather currentlyWeather=darkSkyWeather.getCurrently();
                long temp_timestamp = Long.parseLong(currentlyWeather.getTime())*1000L;
                LocalDateTime triggerTime =
                        LocalDateTime.ofInstant(Instant.ofEpochMilli(temp_timestamp),
                                ZoneId.of(location.getTimezone()));
                Timestamp temptimestamp = Timestamp.valueOf(triggerTime.withMinute(0).withSecond(0));
                currentlyWeather.setTime(temptimestamp.getTime()/1000L+"");
                darkSkyWeather.getHourlyData().add(currentlyWeather);

                darkSkyWeather.getHourlyData().forEach(item->{
                    System.out.println(item.getTime()+" , "+timestampstr);
                    long inner_timestamp = Long.parseLong(item.getTime())*1000L;
                    ZonedDateTime innerTime =
                            ZonedDateTime.ofInstant(Instant.ofEpochMilli(inner_timestamp),
                                    ZoneId.of(location.getTimezone()));
                    Date output = Date.from(innerTime.toInstant());
                    item.setDatel(simpleDateFormat.format(output));
                    item.setDates(simpleDateFormat2.format(output));
                    item.setHourofday(innerTime.getHour()+"");

                    item.set_id(location.get_id()+item.getTime());
                    item.setLocationId(location.get_id());
                    if(timestampstr.trim().equals(item.getTime())){
                        item.setStatus("Original");
                        System.out.println("Original");
                        darkSkyWeather.setCurrently(item);
                    }else{
                        item.setStatus("Forcast");
                    }

                });
                darkSkyWeathers.add(darkSkyWeather);
                darkSkyWeather.getHourlyData().forEach(itemw->{
                    weatherService.saveWeather(itemw);
                });
            }else{
                DarkSkyWeather darkSkyWeather=new DarkSkyWeather();
                List<Weather> hourlyData=(List<Weather>)weatherService.getAllByLocationId(location.get_id());
                hourlyData.forEach(item->{
                    if("Original".equals(item.getStatus())){
                        darkSkyWeather.setCurrently(item);
                    }
                });
                darkSkyWeather.setLocationName(location.getLocationName());
                darkSkyWeather.setLatitude(location.getLocationLatitude());
                darkSkyWeather.setLongitude(location.getLocationLongitude());
                darkSkyWeather.setTimezone(location.getTimezone());
                darkSkyWeather.setHourlyData(hourlyData);
                darkSkyWeathers.add(darkSkyWeather);
            }
        }
        System.out.println(city);
        System.out.println("test data");
        System.out.println(darkSkyWeathers.size());

        model.addAttribute("weatherData", darkSkyWeathers);
        return "weatherdata";
    }

    @RequestMapping(value = "/abc",method= RequestMethod.POST )
    public void getTimeZone(Model model, @RequestBody String s){
        System.out.println(s);
    }
}
