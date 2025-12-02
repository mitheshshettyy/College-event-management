package com.example.demo.controller;

import com.example.demo.model.Event;
import com.example.demo.model.Registration;
import com.example.demo.repository.IEventRepo;
import com.example.demo.repository.IRegistrationRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/student")
public class StudentViewController {

    @Autowired
    IEventRepo eventRepo;

    @Autowired
    IRegistrationRepo regRepo;

    private boolean isStudent(HttpSession session) {
        return "STUDENT".equals(session.getAttribute("role"));
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isStudent(session)) return "redirect:/login";
        Long studentId = (Long)session.getAttribute("studentId");
        model.addAttribute("events", eventRepo.findAll());
        model.addAttribute("registrations", regRepo.findByStudentId(studentId));
        model.addAttribute("studentId", studentId);
        return "student/dashboard";
    }

    @PostMapping("/register/{eventId}")
    public String register(@PathVariable Integer eventId, HttpSession session, Model model) {
        if (!isStudent(session)) return "redirect:/login";
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) return "redirect:/login";

        var existing = regRepo.findByStudentIdAndEventId(studentId, eventId);
        if (existing.isPresent()) {
            model.addAttribute("message", "Already registered for this event");
        } else {
            Registration r = new Registration(studentId, eventId);
            regRepo.save(r);
            model.addAttribute("message", "Successfully registered");
        }
        return "redirect:/student/dashboard";
    }
}
