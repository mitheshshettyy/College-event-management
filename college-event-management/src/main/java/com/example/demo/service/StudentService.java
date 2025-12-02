package com.example.demo.service;

import com.example.demo.model.Department;
import com.example.demo.model.Student;
import com.example.demo.repository.IStudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private IStudentRepo studentRepo;

    public String addAStudent(Student s) {
        studentRepo.save(s);
        return "Student added";
    }

    public String addStudents(List<Student> students) {
        studentRepo.saveAll(students);
        return "Students added";
    }

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepo.findById(id);
    }

    public String updateDepartment(Long id, Department dept) {
        Optional<Student> o = studentRepo.findById(id);
        if (o.isEmpty()) return "Student not found";
        Student s = o.get();
        s.setStudentDepartment(dept);
        studentRepo.save(s);
        return "Department updated";
    }

    public String deleteStudent(Long id) {
        if (!studentRepo.existsById(id)) return "Student not found";
        studentRepo.deleteById(id);
        return "Student deleted";
    }
}
