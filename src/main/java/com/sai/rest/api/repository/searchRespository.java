package com.sai.rest.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.rest.api.entity.Student;

public interface searchRespository extends JpaRepository<Student, Long> {

    // ✅ Search by Name OR Email
    List<Student> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String keyword1,
            String keyword2
    );
}
