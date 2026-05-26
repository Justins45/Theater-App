package com.code.theaterapp.patron;

import com.code.theaterapp.shared.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatronRepo extends JpaRepository<Patron, Long> {

    Optional<Patron> findByPerson(Person person);
}

