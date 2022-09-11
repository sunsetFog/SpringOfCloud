package com.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class flowerController {
    @GetMapping("/water")
    public String waterWay() {
        System.out.println("2OK!");
        return "OK2OK!";
    }
}
