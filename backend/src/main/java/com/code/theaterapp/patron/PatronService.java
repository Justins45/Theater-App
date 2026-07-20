package com.code.theaterapp.patron;

import com.code.theaterapp.auth.dtos.PatronRegisterConfirmationDTO;
import com.code.theaterapp.auth.dtos.PatronRegisterDTO;
import com.code.theaterapp.auth.secruity.accounts.PatronAccount;
import com.code.theaterapp.patron.dtos.PatronAccountDetails;
import com.code.theaterapp.patron.dtos.PatronDetailsDTO;
import com.code.theaterapp.patron.dtos.PatronMeResponse;
import com.code.theaterapp.shared.enums.Role;
import com.code.theaterapp.shared.person.Person;
import com.code.theaterapp.shared.person.PersonService;
import com.code.theaterapp.shared.person.dtos.CreatePersonDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatronService {

    private final PersonService personService;
    private final PatronRepo patronRepo;
    private final PatronMapper patronMapper;

    @Transactional
    public PatronRegisterConfirmationDTO createPatron(PatronRegisterDTO registerDTO) {

        CreatePersonDTO createPersonDTO = new CreatePersonDTO(
                registerDTO.email(),
                registerDTO.password()
        );

        Person person = personService.createPerson(createPersonDTO);

        Patron patron = new Patron();
        patron.setPerson(person);
        patron.setRole(Role.ROLE_PATRON);

        Patron savedPatron = patronRepo.save(patron);
        return patronMapper.toRegisterConfirmation(savedPatron);
    }

    public PatronMeResponse getMe(PatronAccount account) {
        return new PatronMeResponse(
                account.getEmail(),
                account.getFirstname(),
                account.getLastname(),
                account.getDisplayName()
        );
    }

    public ResponseEntity<PatronAccountDetails> getPatron(PatronAccount account) {

        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(patronMapper.toAccountDetails(account));
    }

}
