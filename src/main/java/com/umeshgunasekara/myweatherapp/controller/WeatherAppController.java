package com.umeshgunasekara.myweatherapp.controller;

import com.umeshgunasekara.myweatherapp.entity.Weather;
import com.umeshgunasekara.myweatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
public class WeatherAppController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = "/weather",method= RequestMethod.POST )
    public String homePage(Model model, @ModelAttribute String city) {
        Optional<Weather> weather=weatherService.findWeatherById("5d9a0807867be64c68fe7b001570406400");
        model.addAttribute("weatherData", weather.get());
        return "weatherdata";
    }
}
