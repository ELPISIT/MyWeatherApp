package com.umeshgunasekara.myweatherapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * Created by Umesh Gunasekara on 2019-10-06.
 */
@Controller
public class WelcomeController {

    @Value("${spring.application.name}")
    String appName;

    @RequestMapping(method=RequestMethod.GET, value="/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }

//    @RequestMapping(method=RequestMethod.GET, value="/")
//    public String indexPage() {
//        return "redirect:" + "/weather";
//    }

    @RequestMapping(method=RequestMethod.GET, value="/hi")
    public String hi(){
        return "Hello";
    }



}
