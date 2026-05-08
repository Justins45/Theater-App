package com.theaterapp.patron;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

class PatronMapperTest {

    PatronMapper mapper = new PatronMapper();

    @Test
    @DisplayName("Should map all fields to DTO")
    void shouldMapAllFieldsToDTO() {
        Patron patron = new Patron("Johndoe", "john@example.com");

        PatronDTO dto = mapper.apply(patron);

        assertEquals("Missing Username", "Johndoe", dto.userName());
        assertEquals("Missing Email", "john@example.com", dto.email());
    }

    @Test
    @DisplayName("Should not expose password field")
    void shouldNotExposePassword() {
        // Verifies the DTO doesn't accidentally include sensitive fields
        Patron patron = new Patron("Johndoe", "john@example.com");
        patron.setPassword("supersecret");

        PatronDTO dto = mapper.apply(patron);

        // PatronDTO has no password field — this just confirms the record shape
        assertNotNull(dto);
        assertEquals("has more than 2 items in record", 2,
                PatronDTO.class.getRecordComponents().length);
    }
}
