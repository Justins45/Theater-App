package com.code.theaterapp.shared.person;

import com.code.theaterapp.exceptions.AccountAlreadyExistsException;
import com.code.theaterapp.exceptions.EmailAlreadyExistsException;
import com.code.theaterapp.exceptions.EntityNotFoundException;
import com.code.theaterapp.staff.StaffRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonValidationService {

    private final PersonRepo personRepo;
    private final StaffRepo staffRepo;

    public void validateUniqueEmail(String email) {
        personRepo.findByEmail(email).ifPresent(
                _ -> { throw new EmailAlreadyExistsException(email); }
        );
    }

    public void validateNoStaffAccount(String email) {
        Person person = personRepo.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Person not found")
        );

        staffRepo.findByPerson(person).ifPresent(
                _ -> { throw new AccountAlreadyExistsException("Staff account already exists"); }
        );
    }

}
