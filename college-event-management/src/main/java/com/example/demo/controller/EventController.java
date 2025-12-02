package com.example.demo.controller;

import com.example.demo.model.Event;
import com.example.demo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api") 
public class EventController {

    @Autowired
    private EventService eventService;

    // add single event
    @PostMapping("/event")
    public String addEvent(@RequestBody Event e) {
        return eventService.addAEvent(e);
    }

    // add multiple events
    @PostMapping("/events")
    public String addEvents(@RequestBody List<Event> events) {
        return eventService.addEvents(events);
    }

    // list all
    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/event/{id}")
    public Optional<Event> getEvent(@PathVariable Integer id) {
        return eventService.getEventById(id);
    }

    // find by date: ?date=YYYY-MM-DD
    @GetMapping("/events/date")
    public List<Event> getByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return eventService.getEventsByDate(date);
    }

    // update location
    @PutMapping("/event/id/{id}/location/{loc}")
    public String updateLocation(@PathVariable Integer id, @PathVariable String loc) {
        return eventService.updateLocation(id, loc);
    }

    @DeleteMapping("/event/{id}")
    public String deleteEvent(@PathVariable Integer id) {
        return eventService.deleteEvent(id);
    }
}
