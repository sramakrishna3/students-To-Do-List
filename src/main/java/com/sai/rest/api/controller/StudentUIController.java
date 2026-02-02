package com.sai.rest.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sai.rest.api.entity.Student;
import com.sai.rest.api.service.StudentService;
import com.sai.rest.api.service.StudentServiceImpl;
import com.sai.rest.api.service.searchService;

@Controller
@RequestMapping("/students")
public class StudentUIController {

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private searchService searchSer;

  
    @GetMapping
    public String listStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        Page<Student> studentPage =
                studentService.getStudentsWithPagination(page, size, sortField, sortDir);

        model.addAttribute("students", studentPage.getContent());

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentPage.getTotalPages());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);

        model.addAttribute("reverseSortDir",
                sortDir.equals("asc") ? "desc" : "asc");

        return "students";
    }

  
    @GetMapping("/new")
    public String showNewStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "new_student";
    }

   
    @PostMapping
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.createStudent(student);
        return "redirect:/students";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "edit_student";
    }


    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable("id") long id,
                                @ModelAttribute("student") Student student) {
        studentService.updateStudent(id, student);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }


    @GetMapping("/search")
    public String searchStudents(@RequestParam(required = false) String keyword,
                                 Model model) {

        List<Student> students;

        if (keyword == null || keyword.trim().isEmpty()) {
            students = studentService.getAllStudent();
        } else {
            students = searchSer.searchName(keyword);
        }

        model.addAttribute("students", students);
        model.addAttribute("keyword", keyword);

       
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("sortField", "name");
        model.addAttribute("sortDir", "asc");
        model.addAttribute("reverseSortDir", "desc");

        return "students";
    }
}
