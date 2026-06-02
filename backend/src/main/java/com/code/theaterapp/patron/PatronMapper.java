package com.code.theaterapp.patron;

import com.code.theaterapp.patron.dtos.PatronDetailsDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PatronMapper implements Function<Patron, PatronDetailsDTO> {
    @Override
    public PatronDetailsDTO apply(Patron patron) {
        return new PatronDetailsDTO(
                patron.getPerson().getUsername(),
                patron.getPerson().getEmail(),
                patron.getPerson().getFirstName(),
                patron.getPerson().getLastName()
        );
    }
}
