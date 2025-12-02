package com.example.demo.controller;

import com.example.demo.model.Admin;
import com.example.demo.model.Student;
import com.example.demo.repository.IAdminRepo;
import com.example.demo.repository.IStudentRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AuthController {

    private final IAdminRepo adminRepo;
    private final IStudentRepo studentRepo;

    @Autowired
    public AuthController(IAdminRepo adminRepo, IStudentRepo studentRepo) {
        this.adminRepo = adminRepo;
        this.studentRepo = studentRepo;
    }
    

    /**
     * Show the login page. If already logged in, redirect to the appropriate dashboard.
     */
    @GetMapping({"/login", "/login.html"})
    public String loginForm(HttpSession session) {
        Object role = session.getAttribute("role");
        if ("ADMIN".equals(role)) {
            return "redirect:/admin/dashboard";
        }
        if ("STUDENT".equals(role)) {
            return "redirect:/student/dashboard";
        }
        return "login"; // templates/login.html
    }

   
    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String role,
                              HttpSession session,
                              Model model) {

        if (role == null) {
            model.addAttribute("error", "Please select a role.");
            return "login";
        }

        if ("admin".equalsIgnoreCase(role)) {
            // Admin login flow
            var optAdmin = adminRepo.findByUsername(username);
            if (optAdmin.isPresent()) {
                Admin admin = optAdmin.get();
                // Plaintext password check (demo only). Replace with hashed compare in prod.
                if (admin.getPassword() != null && admin.getPassword().equals(password)) {
                    session.setAttribute("role", "ADMIN");
                    session.setAttribute("username", admin.getUsername());
                    session.setAttribute("adminId", admin.getId());
                    return "redirect:/admin/dashboard";
                } else {
                    model.addAttribute("error", "Invalid admin credentials.");
                    return "login";
                }
            } else {
                model.addAttribute("error", "Admin user not found.");
                return "login";
            }
        } else {
            // Student login flow (demo)
            // Try to match by numeric studentId or by firstName
            Student found = null;
            // try by id if username is numeric
            try {
                long id = Long.parseLong(username);
                found = studentRepo.findById(id).orElse(null);
            } catch (NumberFormatException ignored) {
                // not numeric — fall back to name search below
            }

            if (found == null) {
                // linear search by firstName (simple demo). You may implement a repository method to search.
                for (Student s : studentRepo.findAll()) {
                    if (s.getFirstName() != null && s.getFirstName().equalsIgnoreCase(username)) {
                        found = s;
                        break;
                    }
                }
            }

            if (found != null) {
                // No student password in demo. If you add one, validate it here.
                session.setAttribute("role", "STUDENT");
                session.setAttribute("studentId", found.getStudentId());
                session.setAttribute("username", found.getFirstName());
                return "redirect:/student/dashboard";
            } else {
                model.addAttribute("error", "Student not found. Use studentId or firstName for demo login.");
                return "login";
            }
        }
    }

    /**
     * Logout and invalidate session.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
    
 // Show signup form
    @GetMapping("/signup")
    public String showSignupForm(Model model, HttpSession session) {
        // if already logged in, redirect to appropriate dashboard
        Object role = session.getAttribute("role");
        if ("ADMIN".equals(role)) return "redirect:/admin/dashboard";
        if ("STUDENT".equals(role)) return "redirect:/student/dashboard";

        // provide an empty Student object to bind the form fields
        model.addAttribute("student", new Student());
        return "signup";
    }

    // Handle signup submission
    @PostMapping("/signup")
    public String handleSignup(@ModelAttribute Student student,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        try {
            // Save student to DB
            Student saved = studentRepo.save(student);

            // Store student in session so they are "logged in"
            session.setAttribute("role", "STUDENT");
            // if your Student entity primary key field is studentId (Long) or id, use accordingly
            // here we assume Student has getStudentId() or getId()
            try {
                // try studentId method first
                session.setAttribute("studentId", saved.getStudentId());
            } catch (Exception ex) {
                // fallback to getId
                session.setAttribute("studentId", (saved.getStudentId() != null ? saved.getStudentId() : null));
            }
            session.setAttribute("username", saved.getFirstName());

            // flash message optional
            redirectAttributes.addFlashAttribute("message", "Account created. Welcome, " + saved.getFirstName() + "!");

            // redirect to student dashboard where events are shown
            return "redirect:/student/dashboard";
        } catch (Exception ex) {
            // on error return to form
            redirectAttributes.addFlashAttribute("error", "Could not create account: " + ex.getMessage());
            return "redirect:/signup";
        }
    }

}
