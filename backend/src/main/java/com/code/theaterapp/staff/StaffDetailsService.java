package com.code.theaterapp.staff;

import com.code.theaterapp.auth.secruity.accounts.StaffAccount;
import com.code.theaterapp.shared.person.Person;
import com.code.theaterapp.shared.person.PersonRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffDetailsService implements UserDetailsService {

    private final PersonRepo personRepo;
    private final StaffRepo staffRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not found"));

        Staff staff = staffRepo.findByPerson(person)
                .orElseThrow(() -> new UsernameNotFoundException("No staff profile for this user"));

        return new StaffAccount(person, staff);
    }
}
