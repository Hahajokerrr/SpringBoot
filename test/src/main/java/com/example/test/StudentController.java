package com.example.test;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/student")
    public Student post(
            @RequestBody StudentDto dto
    ) {
        var student = toStudent(dto);
        return repository.save(student);
    }

    private Student toStudent(StudentDto dto) {
        var student = new Student();
        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());
        student.setEmail(dto.email());

        var school = new School();
        school.setId(dto.schoolId());

        student.setSchool(school);

        return student;
    }

    @GetMapping("/students")
    public List<Student> getAllStudent(
    ) {
        return repository.findAll();
    }

    @GetMapping("/students/{student-id}")
    public Student getStudent(
            @PathVariable("student-id") Integer id
    ) {
        return repository.findById(id)
                .orElse(null);
    }

    @GetMapping("/students/search/{student-name}")
    public List<Student> getStudentByName(
            @PathVariable("student-name") String name
    ) {
        return repository.findAllByFirstNameContaining(name);
    }

    @DeleteMapping("/students/{student-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(
            @PathVariable("student-id") Integer id
    ){
        repository.deleteById(id);
    }
}
