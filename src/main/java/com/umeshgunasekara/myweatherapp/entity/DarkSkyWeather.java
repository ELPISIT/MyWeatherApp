package com.umeshgunasekara.myweatherapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
@ToString
@Document(collection = "darkskyweather")
public class DarkSkyWeather {

    private String locationName;
    private double latitude;
    private double longitude;
    private String timezone;
    private Weather currently;
    private List<Weather> hourlyData;

//    @JsonProperty("hourly")
//    public void setCoord(Map<String, Object> hourly) {
//        Gson gson = new Gson();
//        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        String json = gson.toJson(hourly.get("data"),LinkedHashMap.class);
//        System.out.println("json is: "+json);
//        List<Weather> hData=mapper.readValue(json,ArrayList<Weather>.class);
//        setHourlyData((List<Weather>) hourly.get("data"));
//    }

    @JsonProperty("hourly")
    public void setCoord(Map<String, Object> hourly) throws IOException{
        Gson gson = new Gson();
        List<Weather> wlist=new ArrayList<Weather>();
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String json = gson.toJson(hourly.get("data"),ArrayList.class);
        List<LinkedHashMap> dataList=(List<LinkedHashMap>) hourly.get("data");
//        dataList.forEach(item -> {
//            String json2 = gson.toJson(item,LinkedHashMap.class);
//            Weather weather= null;
//            try {
//                weather = mapper.readValue(json2,Weather.class);
//            } catch (IOException e) {
//                throw new IOException(e);
//            }
//            wlist.add(weather);
//        });
        for(LinkedHashMap item : dataList){
            String json2 = gson.toJson(item,LinkedHashMap.class);
            Weather weather=mapper.readValue(json2,Weather.class);
            wlist.add(weather);
        }
        setHourlyData(wlist);
    }

}
