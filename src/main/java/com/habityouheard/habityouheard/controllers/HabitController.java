package com.habityouheard.habityouheard.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/habit")

public class HabitController {

    @GetMapping
    public String hey() {
        return "Hey!";
    }
}
