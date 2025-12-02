package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    private String firstName;
    private String lastName;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Department studentDepartment;

    public Student() {}

    // getters & setters
    
    
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Department getStudentDepartment() { return studentDepartment; }
    public void setStudentDepartment(Department studentDepartment) { this.studentDepartment = studentDepartment; }
}
