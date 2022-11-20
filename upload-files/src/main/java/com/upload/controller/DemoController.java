package com.upload.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin
public class DemoController {
    @GetMapping("/demo12")
    public String demo09() {
        System.out.println("叽叽叽叽姐姐说的很好");
        return "寂静的角落里";
    }
}
