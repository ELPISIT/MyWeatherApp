package com.umeshgunasekara.myweatherapp.dao;

import com.umeshgunasekara.myweatherapp.entity.DarkSkyWeather;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DarkSkyWeatherDao extends MongoRepository<DarkSkyWeather, String> {
}
