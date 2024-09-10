package com.GreenThumb.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class mainController {
    @GetMapping
    public String hello() {
        return "Hello, Spring!";
    }
}
