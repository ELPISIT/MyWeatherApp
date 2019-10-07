package com.umeshgunasekara.myweatherapp.dao;

import com.umeshgunasekara.myweatherapp.entity.Weather;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Created by Umesh Gunasekara on 2019-10-06.
 */
public interface WeatherDao extends MongoRepository<Weather, String> {
    public Optional<Weather> findWeatherBy_idAndStatus(String _id, String status);
    public Iterable<Weather> getAllByLocationId(String locationId);
}
