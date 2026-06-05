package com.code.theaterapp.patron;

import com.code.theaterapp.auth.dtos.PatronRegisterConfirmationDTO;
import com.code.theaterapp.patron.dtos.PatronDetailsDTO;
import org.springframework.stereotype.Component;

@Component
public class PatronMapper {

    public PatronDetailsDTO toDetails(Patron patron) {
        return new PatronDetailsDTO(
                patron.getPerson().getEmail(),
                patron.getPerson().getFirstName(),
                patron.getPerson().getLastName(),
                patron.getPerson().getDisplayName()
        );
    }

    public PatronRegisterConfirmationDTO toRegisterConfirmation(Patron patron) {
        return new PatronRegisterConfirmationDTO(
                patron.getPerson().getEmail()
        );
    }
}
