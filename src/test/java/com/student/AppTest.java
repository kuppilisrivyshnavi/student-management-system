
package com.student;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void testAddStudent() {

        StudentManager manager = new StudentManager();

        Student student = new Student(
                101, "Ravi", "CSE", "ravi@gmail.com"
        );

        manager.addStudent(student);

        assertEquals(1, manager.getAllStudents().size());
    }

    @Test
    void testDuplicateStudentId() {

        StudentManager manager = new StudentManager();

        manager.addStudent(
                new Student(101, "Ravi", "CSE", "ravi@gmail.com")
        );

        assertThrows(IllegalArgumentException.class, () -> {
            manager.addStudent(
                    new Student(101, "Priya", "ECE", "priya@gmail.com")
            );
        });
    }

    @Test
    void testUpdateStudent() {

        StudentManager manager = new StudentManager();

        manager.addStudent(
                new Student(101, "Ravi", "CSE", "ravi@gmail.com")
        );

        boolean result = manager.updateStudent(
                101, "Ravi Kumar", "IT", "ravi@gmail.com"
        );

        assertTrue(result);
        assertEquals("Ravi Kumar", manager.searchStudent(101).getName());
        assertEquals("IT", manager.searchStudent(101).getDepartment());
    }

    @Test
    void testDeleteStudent() {

        StudentManager manager = new StudentManager();

        manager.addStudent(
                new Student(101, "Ravi", "CSE", "ravi@gmail.com")
        );

        boolean result = manager.deleteStudent(101);

        assertTrue(result);
        assertEquals(0, manager.getAllStudents().size());
    }

    @Test
    void testSearchStudent() {

        StudentManager manager = new StudentManager();

        manager.addStudent(
                new Student(101, "Ravi", "CSE", "ravi@gmail.com")
        );

        Student result = manager.searchStudent(101);

        assertNotNull(result);
        assertEquals("Ravi", result.getName());
    }
}
