package com.umeshgunasekara.myweatherapp.service.Impl;

import com.umeshgunasekara.myweatherapp.dao.DarkSkyWeatherDao;
import com.umeshgunasekara.myweatherapp.entity.DarkSkyWeather;
import com.umeshgunasekara.myweatherapp.service.DarkSkyWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Umesh Gunasekara on 2019-10-06.
 */
@Service
public class DarkSkyWeatherServiceImpl implements DarkSkyWeatherService{
    @Autowired
    private DarkSkyWeatherDao darkSkyWeatherDao;

    @Override
    public Iterable<DarkSkyWeather> getAllDarkSkyWeathers() {
        return darkSkyWeatherDao.findAll();
    }

    @Override
    public Optional<DarkSkyWeather> findDarkSkyWeatherById(String darkSkyWeatherId) {
        return darkSkyWeatherDao.findById(darkSkyWeatherId);
    }

    @Override
    public void saveDarkSkyWeather(DarkSkyWeather darkSkyWeather) {
        darkSkyWeatherDao.save(darkSkyWeather);
    }

    @Override
    public void deleteDarkSkyWeatherById(String darkSkyWeatherId) {
        darkSkyWeatherDao.deleteById(darkSkyWeatherId);
    }

    @Override
    public void saveDarkSkyWeathers(Iterable<DarkSkyWeather> darkSkyWeathers) {
        darkSkyWeatherDao.insert(darkSkyWeathers);
    }
}
