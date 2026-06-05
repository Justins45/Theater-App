package com.code.theaterapp.staff;

import com.code.theaterapp.auth.dtos.*;
import com.code.theaterapp.auth.secruity.accounts.StaffAccount;
import com.code.theaterapp.exceptions.EntityNotFoundException;
import com.code.theaterapp.patron.PatronService;
import com.code.theaterapp.shared.person.Person;
import com.code.theaterapp.shared.person.PersonRepo;
import com.code.theaterapp.shared.person.PersonValidationService;
import com.code.theaterapp.staff.dtos.StaffMeResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final PersonValidationService personValidationService;
    private final PersonRepo personRepo;
    private final PatronService patronService;
    private final StaffRepo staffRepo;
    private final StaffMapper staffMapper;

    @Transactional
    public StaffRegisterConfirmationDTO createStaffNoAccount(StaffRegisterDTO requestDTO) {

        personValidationService.validateUniqueEmail(requestDTO.email());

        PatronRegisterDTO patronRegisterDTO = new PatronRegisterDTO(
                requestDTO.password(),
                requestDTO.email()
        );

        PatronRegisterConfirmationDTO patron = patronService.createPatron(patronRegisterDTO);


        personValidationService.validateNoStaffAccount(patron.email());

        Person person = personRepo.findByEmail(patronRegisterDTO.email()).orElseThrow(
                () -> new EntityNotFoundException("Person does not exist")
        );

        // make staff account
        Staff staff = new Staff();
        staff.setPerson(person);
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
                account.getEmail(),
                account.getFirstname(),
                account.getLastname(),
                account.getDisplayName(),
                account.getRole(),
                account.getAccountCreation()
        );
    }
}
