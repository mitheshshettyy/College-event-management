package com.example.demo.controller;

import com.example.demo.model.Event;
import com.example.demo.model.Registration;
import com.example.demo.model.Student;
import com.example.demo.repository.IEventRepo;
import com.example.demo.repository.IRegistrationRepo;
import com.example.demo.repository.IStudentRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    @Autowired
    private IEventRepo eventRepo;

    @Autowired
    private IRegistrationRepo registrationRepo;

    @Autowired
    private IStudentRepo studentRepo;

    private boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("role"));
    }

    /* -------------------------------
       ADMIN DASHBOARD
     ------------------------------- */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        List<Event> events = eventRepo.findAll();
        model.addAttribute("events", events);
        return "admin/dashboard";
    }

    /* -------------------------------
       CREATE EVENT FORM
     ------------------------------- */
    @GetMapping("/events/new")
    public String newEventForm(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("event", new Event());
        return "admin/events-form";
    }

    /* -------------------------------
       SAVE EVENT
     ------------------------------- */
    @PostMapping("/events")
    public String saveEvent(@ModelAttribute Event event, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        eventRepo.save(event);
        return "redirect:/admin/dashboard";
    }

    /* -------------------------------
       EDIT EVENT
     ------------------------------- */
    @GetMapping("/events/edit/{id}")
    public String editEventForm(@PathVariable Integer id, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        Event event = eventRepo.findById(id).orElse(null);
        model.addAttribute("event", event);
        return "admin/events-form";
    }

    /* -------------------------------
       DELETE EVENT
     ------------------------------- */
    @PostMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable Integer id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        eventRepo.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    /* -------------------------------
       VIEW REGISTRATIONS FOR EVENT
     ------------------------------- */
    @GetMapping("/events/{eventId}/registrations")
    public String registrationsForEvent(@PathVariable Integer eventId,
                                        HttpSession session,
                                        Model model,
                                        @ModelAttribute("message") String message,
                                        @ModelAttribute("error") String error) {

        if (!isAdmin(session)) return "redirect:/login";

        Event event = eventRepo.findById(eventId).orElse(null);
        if (event == null) {
            model.addAttribute("error", "Event not found.");
            model.addAttribute("event", null);
            model.addAttribute("registrations", List.of());
            return "admin/event-registrations";
        }

        List<Registration> regs = registrationRepo.findByEventId(eventId);

        // Build a list with student details
        var viewList = regs.stream().map(r -> {
            Student student = studentRepo.findById(r.getStudentId()).orElse(null);
            return new Object() {
                public final Long id = r.getId(); // registration ID
                public final Long studentId = r.getStudentId();
                public final String firstName = student != null ? student.getFirstName() : "Unknown";
                public final String lastName = student != null ? student.getLastName() : "";
                public final String department = (student != null && student.getStudentDepartment() != null)
                        ? student.getStudentDepartment().toString() : "";
                public final java.time.LocalDateTime registeredAt = r.getRegisteredAt();
            };
        }).toList();

        model.addAttribute("event", event);
        model.addAttribute("registrations", viewList);
        model.addAttribute("message", message);
        model.addAttribute("error", error);
        return "admin/event-registrations";
    }

    /* -------------------------------
       DELETE A REGISTRATION
     ------------------------------- */
    @PostMapping("/events/{eventId}/registrations/delete/{registrationId}")
    public String deleteRegistration(@PathVariable Integer eventId,
                                     @PathVariable Long registrationId,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) return "redirect:/login";

        try {
            var regOpt = registrationRepo.findById(registrationId);
            if (regOpt.isPresent()) {
                Registration reg = regOpt.get();
                if (!reg.getEventId().equals(eventId)) {
                    redirectAttributes.addFlashAttribute("error", "Registration does not belong to this event.");
                    return "redirect:/admin/events/" + eventId + "/registrations";
                }
                registrationRepo.deleteById(registrationId);
                redirectAttributes.addFlashAttribute("message", "Student registration removed successfully.");
            } else {
                redirectAttributes.addFlashAttribute("error", "Registration not found.");
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Error deleting registration: " + ex.getMessage());
        }

        return "redirect:/admin/events/" + eventId + "/registrations";
    }
}
