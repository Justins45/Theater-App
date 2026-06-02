package com.code.theaterapp.staff;

import com.code.theaterapp.staff.dtos.StaffDetailsDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class StaffMapper implements Function<Staff, StaffDetailsDTO> {
    @Override
    public StaffDetailsDTO apply(Staff staff) {
        return new StaffDetailsDTO(
                staff.getPerson().getUsername(),
                staff.getPerson().getEmail(),
                staff.getPerson().getFirstName(),
                staff.getPerson().getLastName(),
                staff.getRole()
        );
    }
}

