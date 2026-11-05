
package com.student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentManagementGUI extends JFrame {

    private final StudentManager manager = new StudentManager();

    private JTextField idField;
    private JTextField nameField;
    private JTextField departmentField;
    private JTextField emailField;

    private JTable studentTable;
    private DefaultTableModel tableModel;

    public StudentManagementGUI() {

        setTitle("Student Management System");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 247, 250));

        // Heading
        JLabel heading = new JLabel("Student Management System");
        heading.setFont(new Font("Arial", Font.BOLD, 25));
        heading.setForeground(new Color(35, 55, 85));

        mainPanel.add(heading, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 12));
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));
        formPanel.setBackground(Color.WHITE);

        idField = new JTextField();
        nameField = new JTextField();
        departmentField = new JTextField();
        emailField = new JTextField();

        formPanel.add(new JLabel("Student ID:"));
        formPanel.add(idField);

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Department:"));
        formPanel.add(departmentField);

        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);

        // Buttons
        JButton addButton = new JButton("Add Student");
        JButton updateButton = new JButton("Update Student");
        JButton deleteButton = new JButton("Delete Student");
        JButton searchButton = new JButton("Search Student");
        JButton clearButton = new JButton("Clear");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(new Color(245, 247, 250));
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.CENTER);

        // Student table
        String[] columns = {"ID", "Name", "Department", "Email"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(25);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Student Records"));

        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);

        // Button actions
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        searchButton.addActionListener(e -> searchStudent());
        clearButton.addActionListener(e -> clearFields());

        // Select a table row to fill the form
        studentTable.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int row = studentTable.getSelectedRow();

                if (row >= 0) {
                    idField.setText(tableModel.getValueAt(row, 0).toString());
                    nameField.setText(tableModel.getValueAt(row, 1).toString());
                    departmentField.setText(tableModel.getValueAt(row, 2).toString());
                    emailField.setText(tableModel.getValueAt(row, 3).toString());
                }
            }
        });
    }

    // Add student
    private void addStudent() {

        try {
            int id = Integer.parseInt(idField.getText().trim());

            Student student = new Student(
                    id,
                    nameField.getText().trim(),
                    departmentField.getText().trim(),
                    emailField.getText().trim()
            );

            validateFields(student);

            manager.addStudent(student);

            refreshTable();
            clearFields();

            JOptionPane.showMessageDialog(this, "Student added successfully!");

        } catch (NumberFormatException ex) {
            showError("Please enter a valid numeric Student ID.");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    // Update student
    private void updateStudent() {

        try {
            int id = Integer.parseInt(idField.getText().trim());

            Student student = new Student(
                    id,
                    nameField.getText().trim(),
                    departmentField.getText().trim(),
                    emailField.getText().trim()
            );

            validateFields(student);

            boolean updated = manager.updateStudent(
                    id,
                    student.getName(),
                    student.getDepartment(),
                    student.getEmail()
            );

            if (updated) {
                refreshTable();
                clearFields();
                JOptionPane.showMessageDialog(this, "Student updated successfully!");
            } else {
                showError("Student ID not found.");
            }

        } catch (NumberFormatException ex) {
            showError("Please enter a valid numeric Student ID.");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    // Delete student
    private void deleteStudent() {

        try {
            int id = Integer.parseInt(idField.getText().trim());

            boolean deleted = manager.deleteStudent(id);

            if (deleted) {
                refreshTable();
                clearFields();
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
            } else {
                showError("Student ID not found.");
            }

        } catch (NumberFormatException ex) {
            showError("Please enter a valid numeric Student ID.");
        }
    }

    // Search student
    private void searchStudent() {

        try {
            int id = Integer.parseInt(idField.getText().trim());

            Student student = manager.searchStudent(id);

            if (student != null) {
                nameField.setText(student.getName());
                departmentField.setText(student.getDepartment());
                emailField.setText(student.getEmail());
            } else {
                showError("Student not found.");
            }

        } catch (NumberFormatException ex) {
            showError("Please enter a valid numeric Student ID.");
        }
    }

    // Validate input fields
    private void validateFields(Student student) {

        if (student.getName().isEmpty()
                || student.getDepartment().isEmpty()
                || student.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Please fill in all student details.");
        }

        if (!student.getEmail().contains("@")) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }
    }

    // Refresh table
    private void refreshTable() {

        tableModel.setRowCount(0);

        for (Student student : manager.getAllStudents()) {

            Object[] row = {
                    student.getId(),
                    student.getName(),
                    student.getDepartment(),
                    student.getEmail()
            };

            tableModel.addRow(row);
        }
    }

    // Clear fields
    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        departmentField.setText("");
        emailField.setText("");

        studentTable.clearSelection();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message,
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
