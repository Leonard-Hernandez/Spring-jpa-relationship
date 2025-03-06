package com.andres.curso.springboot.jpa.springboot_jpa_relationship.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.andres.curso.springboot.jpa.springboot_jpa_relationship.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query("SELECT c FROM Client c JOIN FETCH c.addresses WHERE c.id = ?1")
    Optional<Client> findOne(Long id);

}
