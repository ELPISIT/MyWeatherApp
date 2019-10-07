package com.umeshgunasekara.myweatherapp.dao;

import com.umeshgunasekara.myweatherapp.entity.Weather;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Umesh Gunasekara on 2019-10-06.
 */
public interface WeatherDao extends MongoRepository<Weather, String> {
}
