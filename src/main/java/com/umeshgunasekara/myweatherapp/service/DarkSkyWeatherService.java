package com.umeshgunasekara.myweatherapp.service;

import com.umeshgunasekara.myweatherapp.entity.DarkSkyWeather;

import java.util.Optional;

public interface DarkSkyWeatherService {
    /**
     * @apiNote Get All DarkSkyWeathers.
     */
    public Iterable<DarkSkyWeather> getAllDarkSkyWeathers();

    /**
     * @param darkSkyWeatherId as String Object.
     * @return DarkSkyWeather Optional Object.
     * @apiNote Get DarkSkyWeather By darkSkyWeatherId.
     */
    public Optional<DarkSkyWeather> findDarkSkyWeatherById(String darkSkyWeatherId);

    /**
     * @param darkSkyWeather as DarkSkyWeather Object.
     * @apiNote Add DarkSkyWeather.
     */
    public void saveDarkSkyWeather(DarkSkyWeather darkSkyWeather);

    /**
     * @param darkSkyWeatherId as String Object.
     * @apiNote Delete DarkSkyWeather.
     */
    public void deleteDarkSkyWeatherById(String darkSkyWeatherId);

    /**
     * @param darkSkyWeathers as Iterable Object.
     * @apiNote Add All DarkSkyWeathers.
     */
    public void saveDarkSkyWeathers(Iterable<DarkSkyWeather> darkSkyWeathers);
}
