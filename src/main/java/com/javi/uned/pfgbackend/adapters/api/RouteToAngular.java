package com.javi.uned.pfgbackend.adapters.api;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouteToAngular implements ErrorController {

    @GetMapping("/")
    public String home() {
        return "index.html";
    }

    @RequestMapping("/error")
    public String handleError() {
        return "/";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
