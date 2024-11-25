package com.petstore.web_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/web_app_war/")
    public String home() {
        return "index";
    }
}
