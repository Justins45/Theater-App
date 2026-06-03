package com.code.theaterapp.patron;

import com.code.theaterapp.auth.dtos.PatronRegisterConfirmationDTO;
import com.code.theaterapp.auth.dtos.PatronRegisterDTO;
import com.code.theaterapp.auth.secruity.accounts.PatronAccount;
import com.code.theaterapp.exceptions.UsernameAlreadyExistsException;
import com.code.theaterapp.patron.dtos.PatronDetailsDTO;
import com.code.theaterapp.patron.dtos.PatronMeResponse;
import com.code.theaterapp.shared.enums.Role;
import com.code.theaterapp.shared.person.Person;
import com.code.theaterapp.shared.person.PersonRepo;
import com.code.theaterapp.shared.person.PersonService;
import com.code.theaterapp.shared.person.PersonValidationService;
import com.code.theaterapp.shared.person.dtos.CreatePersonDTO;
import com.code.theaterapp.shared.person.dtos.PersonDetailsDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class PatronService {

    private final PersonService personService;
    private final PatronRepo patronRepo;
    private final PatronMapper patronMapper;

    @Transactional
    public PatronRegisterConfirmationDTO createPatron(PatronRegisterDTO registerDTO) {

        CreatePersonDTO createPersonDTO = new CreatePersonDTO(
                registerDTO.username(),
                registerDTO.password(),
                registerDTO.email()
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
                account.getUsername(),
                account.getEmail(),
                account.getFirstname(),
                account.getLastname()
        );
    }

}
