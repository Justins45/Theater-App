package com.code.theaterapp.patron;

import com.code.theaterapp.auth.secruity.accounts.PatronAccount;
import com.code.theaterapp.shared.person.Person;
import com.code.theaterapp.shared.person.PersonRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatronDetailsService implements UserDetailsService {

    private final PersonRepo personRepo;
    private final PatronRepo patronRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not found"));

        Patron patron = patronRepo.findByPerson(person)
                .orElseThrow(() -> new UsernameNotFoundException("No patron profile for this user"));

        return new PatronAccount(person, patron);
    }

}

