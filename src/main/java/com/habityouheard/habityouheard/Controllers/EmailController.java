package com.habityouheard.habityouheard.controllers;

import com.habityouheard.habityouheard.services.SendGridEmail;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Micah Young
 */

@RestController
@CrossOrigin
@RequestMapping("api/email")
public class EmailController {
    @GetMapping("/send")
    public String sendEmail(@RequestBody Map<String, String> json) {
        System.out.println(json.get("email"));
        System.out.println(json.get("message"));

        SendGridEmail.sendEmail(json.get("message"), json.get("email"));
        return "Email sent";

    }


}