package com.code.theaterapp.patron;

import com.code.theaterapp.auth.dtos.PatronRegisterConfirmationDTO;
import com.code.theaterapp.auth.secruity.accounts.PatronAccount;
import com.code.theaterapp.patron.dtos.PatronAccountDetails;
import com.code.theaterapp.patron.dtos.PatronDetailsDTO;
import com.code.theaterapp.patron.dtos.PatronMeResponse;
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

    public PatronMeResponse toMe(PatronAccount account) {
        return new PatronMeResponse(
                account.getEmail(),
                account.getFirstname(),
                account.getLastname(),
                account.getDisplayName()
        );
    }

    public PatronAccountDetails toAccountDetails(PatronAccount account) {
        return new PatronAccountDetails(
                account.getEmail(),
                account.getFirstname(),
                account.getFirstname(),
                account.getDisplayName()
        );
    }
}
