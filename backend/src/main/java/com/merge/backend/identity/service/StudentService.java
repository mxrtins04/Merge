package com.merge.backend.identity.service;

import com.merge.backend.identity.domain.Student;
import java.util.Optional;

public interface StudentService {
    Student registerStudent(Student student);
    Optional<Student> getStudentById(Long id);
    Optional<Student> getStudentByEmail(String email);
    Student updateStudentProfile(Long id, Student studentDetails);
}
