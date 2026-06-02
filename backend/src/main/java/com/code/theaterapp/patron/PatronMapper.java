package com.code.theaterapp.patron;

import com.code.theaterapp.auth.dtos.PatronRegisterConfirmationDTO;
import com.code.theaterapp.patron.dtos.PatronDetailsDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PatronMapper {

    public PatronDetailsDTO toDetails(Patron patron) {
        return new PatronDetailsDTO(
                patron.getPerson().getUsername(),
                patron.getPerson().getEmail(),
                patron.getPerson().getFirstName(),
                patron.getPerson().getLastName()
        );
    }

    public PatronRegisterConfirmationDTO toRegisterConfirmation(Patron patron) {
        return new PatronRegisterConfirmationDTO(
                patron.getPerson().getUsername(),
                patron.getPerson().getEmail()
        );
    }
}
