package com.umeshgunasekara.myweatherapp.service.Impl;

import com.umeshgunasekara.myweatherapp.dao.WeatherDao;
import com.umeshgunasekara.myweatherapp.entity.Weather;
import com.umeshgunasekara.myweatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Umesh Gunasekara on 2019-10-06.
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private WeatherDao weatherDao;

    @Override
    public Iterable<Weather> getAllWeathers() {
        return weatherDao.findAll();
    }

    @Override
    public Optional<Weather> findWeatherById(String weatherId) {
        return weatherDao.findById(weatherId);
    }

    @Override
    public void saveWeather(Weather weather) {
        weatherDao.save(weather);
    }

    @Override
    public void deleteWeatherById(String weatherId) {
        weatherDao.deleteById(weatherId);
    }

    @Override
    public void saveWeatherForcast(Iterable<Weather> weatherForcast) {
        weatherDao.insert(weatherForcast);
    }
}
