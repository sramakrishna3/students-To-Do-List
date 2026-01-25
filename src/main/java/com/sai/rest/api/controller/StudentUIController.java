package com.sai.rest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sai.rest.api.entity.Student;
import com.sai.rest.api.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentUIController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String viewHomePage(Model model) {
        model.addAttribute("students", studentService.getAllStudent());
        return "students"; // students.html
    }

    @GetMapping("/new")
    public String showNewStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "new_student"; // new_student.html
    }

    @PostMapping
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.createStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student); // FIXED: correct attribute name
        return "edit_student"; // edit_student.html
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable("id") long id, @ModelAttribute("student") Student student) {
        studentService.updateStudent(id, student); // FIXED: correct attribute name
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
