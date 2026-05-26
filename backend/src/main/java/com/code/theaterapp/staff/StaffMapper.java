package com.code.theaterapp.staff;

import com.code.theaterapp.staff.dtos.StaffDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class StaffMapper implements Function<Staff, StaffDTO> {
    @Override
    public StaffDTO apply(Staff staff) {
        return new StaffDTO(
                staff.getPerson().getUsername(),
                staff.getPerson().getEmail(),
                staff.getPerson().getFirstName(),
                staff.getPerson().getLastName(),
                staff.getRole()
        );
    }
}

