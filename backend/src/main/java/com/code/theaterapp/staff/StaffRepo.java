package com.code.theaterapp.staff;

import com.code.theaterapp.shared.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Long> {

    Optional<Staff> findByPerson(Person person);
}
