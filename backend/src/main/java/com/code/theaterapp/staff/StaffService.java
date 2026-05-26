package com.code.theaterapp.staff;

import com.code.theaterapp.auth.dtos.StaffRegisterDTO;
import com.code.theaterapp.exceptions.AccountAlreadyExistsException;
import com.code.theaterapp.exceptions.UsernameAlreadyExistsException;
import com.code.theaterapp.patron.Patron;
import com.code.theaterapp.patron.PatronRepo;
import com.code.theaterapp.shared.enums.Role;
import com.code.theaterapp.shared.person.Person;
import com.code.theaterapp.shared.person.PersonRepo;
import com.code.theaterapp.staff.dtos.StaffDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final PersonRepo personRepo;
    private final StaffRepo staffRepo;
    private final PatronRepo patronRepo;
    private final StaffMapper staffMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public StaffDTO createStaff(StaffRegisterDTO requestDTO) {

        // TODO: ?? make checks / person + patron creating into another file? (Duplicated in PatronService)

        Person checkPerson = personRepo.findByUsername(requestDTO.username()).orElse(null);

        if (checkPerson == null) {

            if (personRepo.findByUsername(requestDTO.username()).isPresent()) {
                throw new UsernameAlreadyExistsException("Username is already taken");
            }
            // Make generic person
            Person person = new Person();
            person.setUsername(requestDTO.username());
            person.setEmail(requestDTO.email());
            person.setPassword(bCryptPasswordEncoder.encode(requestDTO.password()));
            person.setAccountCreated(OffsetDateTime.now());

            checkPerson = personRepo.save(person);

            // make Patron as each staff member needs a patron account
            Patron patron = new Patron();
            patron.setPerson(checkPerson);
            patron.setRole(Role.ROLE_PATRON);

            patronRepo.save(patron);
        }

        if (staffRepo.findByPerson(checkPerson).isPresent()) {
            throw new AccountAlreadyExistsException("Staff Account already exists");
        }

        // make staff account
        Staff staff = new Staff();
        staff.setPerson(checkPerson);
        staff.setStaffAccountCreation(OffsetDateTime.now());
        staff.setRole(requestDTO.role());

        Staff staffSaved = staffRepo.save(staff);
        return staffMapper.apply(staffSaved);
    }

}
