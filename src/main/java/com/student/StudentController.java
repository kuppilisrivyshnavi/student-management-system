
package com.student;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/")
    public String dashboard(
            @RequestParam(required = false) String keyword,
            Model model) {

        List<StudentRecord> students;

        if (keyword != null && !keyword.trim().isEmpty()) {
            students = studentRepository
                    .findByNameContainingIgnoreCaseOrDepartmentContainingIgnoreCaseOrEmailContainingIgnoreCase(
                            keyword, keyword, keyword);
        } else {
            students = studentRepository.findAll();
        }

        model.addAttribute("students", students);
        model.addAttribute("totalStudents", studentRepository.count());
        model.addAttribute("keyword", keyword);

        return "index";
    }

    @GetMapping("/students/new")
    public String newStudent(Model model) {
        model.addAttribute("student", new StudentRecord());
        model.addAttribute("pageTitle", "Add Student");
        model.addAttribute("existingId", null);
        return "student-form";
    }

    @PostMapping("/students/save")
    public String saveStudent(
            @Valid @ModelAttribute("student") StudentRecord student,
            BindingResult result,
            @RequestParam(required = false) Integer existingId,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("pageTitle",
                    existingId == null ? "Add Student" : "Edit Student");
            model.addAttribute("existingId", existingId);
            return "student-form";
        }

        if (existingId != null) {

            StudentRecord existingStudent = studentRepository
                    .findById(existingId)
                    .orElseThrow(() ->
                            new IllegalArgumentException("Student not found"));

            existingStudent.setName(student.getName());
            existingStudent.setDepartment(student.getDepartment());
            existingStudent.setEmail(student.getEmail());

            studentRepository.save(existingStudent);

        } else {

            if (student.getId() == null ||
                    studentRepository.existsById(student.getId())) {

                result.rejectValue(
                        "id", "duplicate",
                        "This Student ID already exists. Please enter another ID.");

                model.addAttribute("pageTitle", "Add Student");
                model.addAttribute("existingId", null);

                return "student-form";
            }

            studentRepository.save(student);
        }

        return "redirect:/";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudent(@PathVariable int id, Model model) {

        StudentRecord student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid student ID: " + id));

        model.addAttribute("student", student);
        model.addAttribute("pageTitle", "Edit Student");
        model.addAttribute("existingId", student.getId());

        return "student-form";
    }

    @PostMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable int id) {

        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        }

        return "redirect:/";
    }
}

