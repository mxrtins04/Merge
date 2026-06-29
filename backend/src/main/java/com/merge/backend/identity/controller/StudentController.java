package com.merge.backend.identity.controller;

import com.merge.backend.identity.domain.Student;
import com.merge.backend.identity.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        Student registered = studentService.registerStudent(student);
        return new ResponseEntity<>(registered, HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<Student> getOwnProfile() {
        // TODO: Integrate with security context to get logged-in student info
        // Dummy return for skeleton purposes
        return studentService.getStudentById(1L)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/me")
    public ResponseEntity<Student> updateProfile(@RequestBody Student studentDetails) {
        // TODO: Integrate with security context to get logged-in student info
        Student updated = studentService.updateStudentProfile(1L, studentDetails);
        return ResponseEntity.ok(updated);
    }
}
