package com.example.demo.service;

import com.example.demo.model.Event;
import com.example.demo.repository.IEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private IEventRepo eventRepo;

    public String addAEvent(Event e) {
        eventRepo.save(e);
        return "A new event is added !!!";
    }

    public String addEvents(List<Event> events) {
        eventRepo.saveAll(events);
        return "Events added !!!";
    }

    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public Optional<Event> getEventById(Integer id) {
        return eventRepo.findById(id);
    }

    public List<Event> getEventsByDate(LocalDate date) {
        return eventRepo.findByEventDate(date);
    }

    public String updateLocation(Integer id, String newLoc) {
        Optional<Event> o = eventRepo.findById(id);
        if (o.isEmpty()) return "Event not found";
        Event e = o.get();
        e.setLocationOfEvent(newLoc);
        eventRepo.save(e);
        return "Location updated";
    }

    public String deleteEvent(Integer id) {
        if (!eventRepo.existsById(id)) return "Event not found";
        eventRepo.deleteById(id);
        return "Event deleted";
    }
}
