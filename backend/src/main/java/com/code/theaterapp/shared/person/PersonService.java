package com.code.theaterapp.shared.person;

import ch.qos.logback.core.util.StringUtil;
import com.code.theaterapp.exceptions.EntityNotFoundException;
import com.code.theaterapp.patron.dtos.PatronNamingPatch;
import com.code.theaterapp.patron.dtos.PatronNamingRemovalPatch;
import com.code.theaterapp.shared.person.dtos.CreatePersonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonValidationService personValidationService;
    private final PersonRepo personRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Person createPerson(CreatePersonDTO createPerson) {
        personValidationService.validateUniqueEmail(createPerson.email());

        Person person = new Person();
        person.setPassword(bCryptPasswordEncoder.encode(createPerson.password()));
        person.setEmail(createPerson.email());
        person.setAccountCreated(OffsetDateTime.now());

        return personRepo.save(person);
    }

    public Person updatePersonNames(UUID personId, PatronNamingPatch patch) {
        Person existingPerson = personRepo.findById(personId).orElseThrow(
                () -> new EntityNotFoundException("Person Not found")
        );

        // Set first name
        if (!StringUtil.isNullOrEmpty(patch.firstName())) {
            existingPerson.setFirstName(patch.firstName());
        }

        // Set last name
        if (!StringUtil.isNullOrEmpty(patch.lastName())) {
            existingPerson.setLastName(patch.lastName());
        }

        // Set display name
        if (!StringUtil.isNullOrEmpty(patch.displayName())) {
            existingPerson.setDisplayName(patch.displayName());
        }

        return personRepo.save(existingPerson);

    }

    public Person removePersonNames(UUID personId, PatronNamingRemovalPatch patch) {
        Person existingPerson = personRepo.findById(personId).orElseThrow(
                () -> new EntityNotFoundException("Person Not found")
        );

        // unset first / last / display names
        if (patch.firstName()) {
            existingPerson.setFirstName(null);
        }
        if (patch.lastName()) {
            existingPerson.setLastName(null);
        }
        if (patch.displayName()) {
            existingPerson.setDisplayName(null);
        }

        return personRepo.save(existingPerson);
    }
}
