package com.code.theaterapp.shared.person;

import com.code.theaterapp.shared.person.dtos.CreatePersonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonValidationService personValidationService;
    private final PersonRepo personRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Person createPerson(CreatePersonDTO createPerson) {

        personValidationService.validateUniqueEmail(createPerson.email());
        personValidationService.validateUniqueUsername(createPerson.username());

        Person person = new Person();
        person.setUsername(createPerson.username());
        person.setPassword(bCryptPasswordEncoder.encode(createPerson.password()));
        person.setEmail(createPerson.email());
        person.setAccountCreated(OffsetDateTime.now());

        return personRepo.save(person);
    }
}
