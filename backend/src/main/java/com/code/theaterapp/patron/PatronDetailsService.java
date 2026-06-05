package com.code.theaterapp.patron;

import com.code.theaterapp.auth.secruity.accounts.PatronAccount;
import com.code.theaterapp.exceptions.EntityNotFoundException;
import com.code.theaterapp.shared.person.Person;
import com.code.theaterapp.shared.person.PersonRepo;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatronDetailsService implements UserDetailsService {

    private final PersonRepo personRepo;
    private final PatronRepo patronRepo;

    // NOTE: Email is Username here
    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws EntityNotFoundException {
        Person person = personRepo.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User Not found"));

        Patron patron = patronRepo.findByPerson(person)
                .orElseThrow(() -> new EntityNotFoundException("No patron profile for this user"));

        return new PatronAccount(person, patron);
    }

}

