package com.theaterapp.patron;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PatronMapper implements Function<Patron, PatronDTO> {
    @Override
    public PatronDTO apply(Patron patron) {
        return new PatronDTO(
                patron.getFirstName(),
                patron.getLastName(),
                patron.getEmail()
        );
    }
}
