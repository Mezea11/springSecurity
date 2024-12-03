package com.example.springSecurity.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
public class CommonController {

    @GetMapping
    public String helloCommon() {
        return "Hello Common Controller";
    }
}
