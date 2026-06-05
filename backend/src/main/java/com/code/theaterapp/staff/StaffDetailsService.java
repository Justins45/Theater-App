package com.code.theaterapp.staff;

import com.code.theaterapp.auth.secruity.accounts.StaffAccount;
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
public class StaffDetailsService implements UserDetailsService {

    private final PersonRepo personRepo;
    private final StaffRepo staffRepo;

    // NOTE: Email is the username
    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws EntityNotFoundException {
        Person person = personRepo.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User Not found"));

        Staff staff = staffRepo.findByPerson(person)
                .orElseThrow(() -> new EntityNotFoundException("No staff profile for this user"));

        return new StaffAccount(person, staff);
    }
}
