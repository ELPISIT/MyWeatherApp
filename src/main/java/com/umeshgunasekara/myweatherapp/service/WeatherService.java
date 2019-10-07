package com.umeshgunasekara.myweatherapp.service;


import com.umeshgunasekara.myweatherapp.entity.Weather;

import java.util.Optional;

/**
 * Created by Umesh Gunasekara on 2019-10-06.
 */
public interface WeatherService {
    /**
     * @apiNote Get All Weather.
     */
    public Iterable<Weather> getAllWeathers();

    /**
     * @param weatherId as String Object.
     * @return Weather Optional Object.
     * @apiNote Get Weather By weatherId.
     */
    public Optional<Weather> findWeatherById(String weatherId);

    /**
     * @param weather as Weather Object.
     * @apiNote Add Weather.
     */
    public void saveWeather(Weather weather);

    /**
     * @param weatherId as String Object.
     * @apiNote Delete Weather.
     */
    public void deleteWeatherById(String weatherId);

    /**
     * @param weatherForcast as Iterable Object.
     * @apiNote Add All Weather Recodes.
     */
    public void saveWeatherForcast(Iterable<Weather> weatherForcast);
}
