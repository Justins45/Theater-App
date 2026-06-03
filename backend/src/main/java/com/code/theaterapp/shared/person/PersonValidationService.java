package com.code.theaterapp.shared.person;

import com.code.theaterapp.exceptions.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonValidationService {

    private final PersonRepo personRepo;

    /*
     *  TODO: Things to make
     *
     *   PATRON CHECKS
     *  1. check if email is taken
     *
     *  STAFF CHECKS
     *  1. Check Person Exists (brand new account)
     *  2. Return person to add Staff account to (existing account)
     *  3. check if staff account exists
     */


    public void validateUniqueEmail(String email) {
        personRepo.findByEmail(email).ifPresent(
                p -> { throw new EmailAlreadyExistsException(email); }
        );
    }

}
