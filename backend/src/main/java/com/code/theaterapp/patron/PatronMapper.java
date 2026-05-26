package com.code.theaterapp.patron;

import com.code.theaterapp.patron.dtos.PatronDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PatronMapper implements Function<Patron, PatronDTO> {
    @Override
    public PatronDTO apply(Patron patron) {
        return new PatronDTO(
                patron.getPerson().getUsername(),
                patron.getPerson().getEmail(),
                patron.getPerson().getFirstName(),
                patron.getPerson().getLastName()
        );
    }
}
