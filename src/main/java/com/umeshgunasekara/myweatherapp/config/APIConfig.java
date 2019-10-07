package com.umeshgunasekara.myweatherapp.config;

import com.umeshgunasekara.myweatherapp.resources.DarkSkyUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Umesh Gunasekara on 2019-10-06.
 */
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan
public class APIConfig {

    @Value("${darksky.url}")
    private String url;

    @Value("${darksky.apikey}")
    private String apikey;

    @Bean
    public DarkSkyUrl generateDarkSkyUrl() {

        DarkSkyUrl darkSkyUrl = new DarkSkyUrl();
        darkSkyUrl.setUrl(url);
        darkSkyUrl.setApiKey(apikey);
        return darkSkyUrl;
    }
}
