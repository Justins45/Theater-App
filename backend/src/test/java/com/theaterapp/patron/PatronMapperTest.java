package com.theaterapp.patron;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

class PatronMapperTest {

    PatronMapper mapper = new PatronMapper();

    @Test
    void shouldMapAllFieldsToDTO() {
        Patron patron = new Patron("John", "Doe", "john@example.com");

        PatronDTO dto = mapper.apply(patron);

        assertEquals("Missing First Name","John", dto.firstName());
        assertEquals("Missing Last Name","Doe", dto.lastName());
        assertEquals("Missing Email", "john@example.com", dto.email());
    }

    @Test
    void shouldNotExposePassword() {
        // Verifies the DTO doesn't accidentally include sensitive fields
        Patron patron = new Patron("John", "Doe", "john@example.com");
        patron.setPassword("supersecret");

        PatronDTO dto = mapper.apply(patron);

        // PatronDTO has no password field — this just confirms the record shape
        assertNotNull(dto);
        assertEquals("has more than 3 items in record", 3,
                PatronDTO.class.getRecordComponents().length);
    }
}
