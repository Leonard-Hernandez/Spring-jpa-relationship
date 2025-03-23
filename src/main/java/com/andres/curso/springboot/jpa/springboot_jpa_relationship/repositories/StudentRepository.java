package com.andres.curso.springboot.jpa.springboot_jpa_relationship.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.andres.curso.springboot.jpa.springboot_jpa_relationship.entities.Student;

public interface StudentRepository  extends CrudRepository<Student, Long> {

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.courses Where s.id = ?1")
    Optional<Student> findWithAll(Long id);

}
