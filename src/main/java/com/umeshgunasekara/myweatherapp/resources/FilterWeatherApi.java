package com.umeshgunasekara.myweatherapp.resources;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.umeshgunasekara.myweatherapp.entity.DarkSkyWeather;
import com.umeshgunasekara.myweatherapp.entity.Weather;

import java.io.IOException;
import java.util.*;

public class FilterWeatherApi {

    public static DarkSkyWeather generateDarkSkyWeather(String data, String id) throws IOException {

        System.out.println("datais: "+data);

        DarkSkyWeather weather=new DarkSkyWeather();

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        weather=mapper.readValue(data,DarkSkyWeather.class);
        System.out.println("dark sky weather : "+weather.getTimezone());

        Map<String,Object> myMap=mapper.readValue(data,HashMap.class);

        System.out.println("Map is: "+myMap);

        weather.setLatitude((double) myMap.get("latitude"));
        weather.setLongitude((double) myMap.get("longitude"));
        weather.setTimezone((String) myMap.get("timezone"));

//        String str=(String)myMap.get("currently");
//        System.out.println("str is: "+str);

        Gson gson = new Gson();
        String json = gson.toJson(myMap.get("currently"),LinkedHashMap.class);
        System.out.println("json is: "+json);
        Weather currently=mapper.readValue(json,Weather.class);

        weather.setCurrently(currently);

        ArrayList<Map> hourly= (ArrayList<Map>) ((Map)myMap.get("hourly")).get("data");
        List<Weather> wlist=new ArrayList<Weather>();
        for (Map mp:hourly) {
            String json2 = gson.toJson(mp,HashMap.class);
            System.out.println("json2 is: "+json2);
            Weather currently2=mapper.readValue(json2,Weather.class);
            currently2.setLocationId(id);
            wlist.add(currently2);
        }
        weather.setHourlyData(wlist);
        return weather;
    }
}
