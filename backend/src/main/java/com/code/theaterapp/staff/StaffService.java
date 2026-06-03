package com.code.theaterapp.staff;

import com.code.theaterapp.auth.dtos.StaffRegisterConfirmationDTO;
import com.code.theaterapp.auth.dtos.StaffRegisterDTO;
import com.code.theaterapp.auth.secruity.accounts.StaffAccount;
import com.code.theaterapp.exceptions.AccountAlreadyExistsException;
import com.code.theaterapp.exceptions.EntityNotFoundException;
import com.code.theaterapp.exceptions.UsernameAlreadyExistsException;
import com.code.theaterapp.patron.Patron;
import com.code.theaterapp.patron.PatronRepo;
import com.code.theaterapp.shared.enums.Role;
import com.code.theaterapp.shared.person.Person;
import com.code.theaterapp.shared.person.PersonRepo;
import com.code.theaterapp.shared.person.PersonValidationService;
import com.code.theaterapp.staff.dtos.StaffDetailsDTO;
import com.code.theaterapp.staff.dtos.StaffMeResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final PersonValidationService personValidationService;
    private final PersonRepo personRepo;
    private final StaffRepo staffRepo;
    private final PatronRepo patronRepo;
    private final StaffMapper staffMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public StaffRegisterConfirmationDTO createStaffNoAccount(StaffRegisterDTO requestDTO) {

        personValidationService.validateUniqueEmail(requestDTO.email());
        personValidationService.validateUniqueUsername(requestDTO.username());


        // Make generic person
        Person person = new Person();
        person.setUsername(requestDTO.username());
        person.setEmail(requestDTO.email());
        person.setPassword(bCryptPasswordEncoder.encode(requestDTO.password()));
        person.setAccountCreated(OffsetDateTime.now());

        Person savedPerson = personRepo.save(person);

        // make Patron as each staff member needs a patron account
        Patron patron = new Patron();
        patron.setPerson(savedPerson);
        patron.setRole(Role.ROLE_PATRON);

        patronRepo.save(patron);


        personValidationService.validateNoStaffAccount(savedPerson.getEmail());

        // make staff account
        Staff staff = new Staff();
        staff.setPerson(savedPerson);
        staff.setStaffAccountCreation(OffsetDateTime.now());
        staff.setRole(requestDTO.role());

        Staff staffSaved = staffRepo.save(staff);
        return staffMapper.toRegisterConfirmation(staffSaved);
    }

    @Transactional
    public StaffRegisterConfirmationDTO createStaffExistingAccount(StaffRegisterDTO requestDTO) {

        Person person = personRepo.findByEmail(requestDTO.email()).orElseThrow(
                () -> new EntityNotFoundException("Person not found")
        );

        personValidationService.validateNoStaffAccount(person.getEmail());

        // make staff account
        Staff staff = new Staff();
        staff.setPerson(person);
        staff.setStaffAccountCreation(OffsetDateTime.now());
        staff.setRole(requestDTO.role());

        Staff staffSaved = staffRepo.save(staff);
        return staffMapper.toRegisterConfirmation(staffSaved);
    }

    @Transactional
    public StaffMeResponse getMe(StaffAccount account) {
        return new StaffMeResponse(
                account.getUsername(),
                account.getEmail(),
                account.getFirstname(),
                account.getLastname(),
                account.getRole(),
                account.getAccountCreation()
        );
    }
}
