package com.theaterapp.secruity;


import com.theaterapp.patron.PatronDTO;
import com.theaterapp.patron.PatronRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@RequiredArgsConstructor
public class DetailsService implements UserDetailsService {

    private final PatronRepository patronRepository;

    /**
     * Says username but can be any identifying field.
     * @param email the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        PatronDTO patron = patronRepository.getUserByEmail(email);
        if (patron == null) {
            throw new UsernameNotFoundException("User not found with email: +" +
                    " ", email);
        }

        return new org.springframework.security.core.userdetails.User(
                patron.email(),
                patron.getPassword(),
                Collections.emptyList()
        );
    }
}
