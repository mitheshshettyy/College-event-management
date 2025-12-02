package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api") 
public class StudentController {

    @Autowired
    private StudentService studentService;

    // add single
    @PostMapping("/student")
    public String addStudent(@RequestBody Student s) {
        return studentService.addAStudent(s);
    }

    // add multiple
    @PostMapping("/students")
    public String addStudents(@RequestBody List<Student> students) {
        return studentService.addStudents(students);
    }

    // list all
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/student/{id}")
    public Optional<Student> getStudent(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    // update department: /student/{id}/{department}
    @PutMapping("/student/{id}/{department}")
    public String updateDepartment(@PathVariable Long id, @PathVariable String department) {
        Department dept;
        try {
            dept = Department.valueOf(department);
        } catch (IllegalArgumentException ex) {
            return "Invalid department value";
        }
        return studentService.updateDepartment(id, dept);
    }

    @DeleteMapping("/student/{id}")
    public String deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }
}
