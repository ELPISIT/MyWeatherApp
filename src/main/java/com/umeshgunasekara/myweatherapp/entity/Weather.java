package com.umeshgunasekara.myweatherapp.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Umesh Gunasekara on 2019-10-06.
 */
@Data
@ToString
@Document(collection = "weather")
public class Weather {

    private String locationId;
    @Id
    private String time;
    private String summary;
    private String icon;
    private double nearestStormDistance;
    private double precipIntensity;
    private double precipProbability;
    private String precipType;
    private double temperature;
    private double apparentTemperature;
    private double dewPoint;
    private double humidity;
    private double pressure;
    private double windSpeed;
    private double windGust;
    private double windBearing;
    private double cloudCover;
    private double uvIndex;
    private double visibility;
    private double ozone;

}
