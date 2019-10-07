package com.umeshgunasekara.myweatherapp.dao;

import com.umeshgunasekara.myweatherapp.entity.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Created by Umesh Gunasekara on 2019-10-06.
 */
public interface LocationDao extends MongoRepository<Location, String> {
    public Optional<Location> findLocationByLocationName(String locattionName);
}
