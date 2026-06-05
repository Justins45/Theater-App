package com.code.theaterapp.shared.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepo extends JpaRepository<Person, UUID> {
    Optional<Person> findByEmail(String email);
}
