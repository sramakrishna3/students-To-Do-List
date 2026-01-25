package com.sai.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.rest.api.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

}
