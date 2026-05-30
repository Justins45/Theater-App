package com.code.theaterapp.patron;

import com.code.theaterapp.shared.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatronRepo extends JpaRepository<Patron, UUID> {

    Optional<Patron> findByPerson(Person person);
}

