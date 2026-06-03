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
import com.code.theaterapp.shared.person.PersonValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class PatronService {

    private final PersonValidationService personValidationService;
    private final PersonRepo personRepo;
    private final PatronRepo patronRepo;
    private final PatronMapper patronMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public PatronRegisterConfirmationDTO createPatron(PatronRegisterDTO registerDTO) {

        personValidationService.validateUniqueEmail(registerDTO.email());
        personValidationService.validateUniqueUsername(registerDTO.username());

        Person person = new Person();
        person.setUsername(registerDTO.username());
        person.setPassword(bCryptPasswordEncoder.encode(registerDTO.password()));
        person.setEmail(registerDTO.email());
        person.setAccountCreated(OffsetDateTime.now());

        Person savedPerson = personRepo.save(person);

        Patron patron = new Patron();
        patron.setPerson(savedPerson);
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
