package com.code.theaterapp.staff;

import com.code.theaterapp.auth.dtos.StaffRegisterConfirmationDTO;
import com.code.theaterapp.staff.dtos.StaffDetailsDTO;
import org.springframework.stereotype.Component;

@Component
public class StaffMapper {
    public StaffDetailsDTO toDetails(Staff staff) {
        return new StaffDetailsDTO(
                staff.getPerson().getEmail(),
                staff.getPerson().getFirstName(),
                staff.getPerson().getLastName(),
                staff.getPerson().getDisplayName(),
                staff.getRole()
        );
    }

    public StaffRegisterConfirmationDTO toRegisterConfirmation(Staff staff) {
        return new StaffRegisterConfirmationDTO(
                staff.getPerson().getEmail(),
                staff.getRole()
        );
    }
}

