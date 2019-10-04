package com.umeshgunasekara.myweatherapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @RequestMapping("/hi")
    public String hi(){
        return "Hello";
    }
}
