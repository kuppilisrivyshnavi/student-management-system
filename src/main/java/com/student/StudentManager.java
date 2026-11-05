package com.student;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {

    private List<Student> students = new ArrayList<>();

    // Add a new student
    public void addStudent(Student student) {

        for (Student s : students) {
            if (s.getId() == student.getId()) {
                throw new IllegalArgumentException("Student ID already exists!");
            }
        }

        students.add(student);
    }

    // View all students
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    // Update student details
    public boolean updateStudent(int id, String name, String department, String email) {

        for (Student s : students) {
            if (s.getId() == id) {
                s.setName(name);
                s.setDepartment(department);
                s.setEmail(email);
                return true;
            }
        }

        return false;
    }

    // Delete a student
    public boolean deleteStudent(int id) {

        return students.removeIf(s -> s.getId() == id);
    }

    // Search student by ID
    public Student searchStudent(int id) {

        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }

        return null;
    }
}