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
@Document(collection = "location")
public class Location {

    @Id
    private String _id;
    private String locationName;
    private String timezone;
    private double locationLongitude;
    private double locationLatitude;
}
