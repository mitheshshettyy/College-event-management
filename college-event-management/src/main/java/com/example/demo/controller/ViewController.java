package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    // Home page
	@GetMapping("/index-page")
	public String index() {
	    return "index";
	}
    // Fallback: public events page (optional)
    @GetMapping("/events")
    public String events() {
        return "student/dashboard"; // or "events" if you have a separate public page
    }
}
