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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @RequestMapping(method= RequestMethod.POST, value = "/weather")
    public String watherDetailsPage(Model model, @RequestParam String city) throws IOException{
        List<Location> locationList= (List<Location>) locationService.getAllLocations();
        List<DarkSkyWeather> darkSkyWeathers=new ArrayList<DarkSkyWeather>();

        Map<String, ZoneId> timezones=new HashMap<String, ZoneId>();
        String pattern = "EEEEE MMMMM yyyy", pattern2 = "yyyy-MM-dd", tState="Original";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);

        for(Location location:locationList){
            timezones.put(location.getLocationName(),ZoneId.of(location.getTimezone()));
            Timestamp timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of(location.getTimezone())).withMinute(0).withSecond(0).toLocalDateTime());
            long locatonTimeStamp=(timestamp.getTime()/1000L);
            Optional<Weather> weather=weatherService.findWeatherBy_idAndStatus(location.get_id()+""+locatonTimeStamp, tState);
            if(!weather.isPresent()){
                UriComponents uriComponents = UriComponentsBuilder
                        .newInstance()
                        .scheme("https")
                        .host(darkSkyUrl.getUrl())
                        .path("/{apikey}/{lat},{lon}")
                        .buildAndExpand(darkSkyUrl.getApiKey(),location.getLocationLatitude(),location.getLocationLongitude());

                String uri = uriComponents.toUriString();
                ResponseEntity<String> resp= restTemplate.exchange(uri, HttpMethod.GET, null, String.class);

                DarkSkyWeather darkSkyWeather = objectMapper.readValue(resp.getBody(), DarkSkyWeather.class);
                darkSkyWeather.setLocationName(location.getLocationName());
                Weather currentlyWeather=darkSkyWeather.getCurrently();
                long temp_timestamp = currentlyWeather.getTime()*1000L;
                LocalDateTime triggerTime =LocalDateTime.ofInstant(Instant.ofEpochMilli(temp_timestamp),
                                ZoneId.of(location.getTimezone()));
                Timestamp temptimestamp = Timestamp.valueOf(triggerTime.withMinute(0).withSecond(0));
                currentlyWeather.setTime(temptimestamp.getTime()/1000L);
                darkSkyWeather.getHourlyData().add(currentlyWeather);

                List<Weather> hourlyData2=darkSkyWeather.getHourlyData().stream().filter(a->(a.getTime()>=locatonTimeStamp&&a.getTime()<=(locatonTimeStamp+(3600*24)))).collect(Collectors.toList());
                darkSkyWeather.setHourlyData(hourlyData2);
                darkSkyWeather.getHourlyData().forEach(item->{
                    long inner_timestamp = item.getTime()*1000L;
                    ZonedDateTime innerTime =ZonedDateTime.ofInstant(Instant.ofEpochMilli(inner_timestamp),
                                    ZoneId.of(location.getTimezone()));
                    Date output = Date.from(innerTime.toInstant());
                    item.setDatel(simpleDateFormat.format(output));
                    item.setDates(simpleDateFormat2.format(output));
                    String hourOfDay=(innerTime.getHour()>12)?(innerTime.getHour()-12)+" PM":innerTime.getHour()+" AM";
                    item.setHourofday(hourOfDay);

                    item.set_id(location.get_id()+""+item.getTime());
                    item.setLocationId(location.get_id());
                    if(locatonTimeStamp==item.getTime()){
                        item.setStatus(tState);
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
                List<Weather> hourlyData1=hourlyData.stream().filter(a->a.getTime()<=(locatonTimeStamp-(3600*24*3))).collect(Collectors.toList());
                if(!hourlyData1.isEmpty()){
                    weatherService.deleteWeathers(hourlyData1);
                }
                List<Weather> hourlyData2=hourlyData.stream().filter(a->(a.getTime()>=locatonTimeStamp&&a.getTime()<=(locatonTimeStamp+(3600*24)))).collect(Collectors.toList());
                darkSkyWeather.setLocationName(location.getLocationName());
                darkSkyWeather.setLatitude(location.getLocationLatitude());
                darkSkyWeather.setLongitude(location.getLocationLongitude());
                darkSkyWeather.setTimezone(location.getTimezone());
                darkSkyWeather.setHourlyData(hourlyData2);
                darkSkyWeathers.add(darkSkyWeather);
            }
        }

        model.addAttribute("weatherData", darkSkyWeathers);
        return "weatherdata";
    }
}
