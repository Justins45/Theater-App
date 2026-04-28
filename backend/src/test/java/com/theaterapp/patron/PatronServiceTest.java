package com.theaterapp.patron;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PatronServiceTest {

    @Mock
    PatronRepository patronRepository;

    @Mock
    PatronMapper patronMapper;

    @InjectMocks
    PatronService patronService;

    @Test
    void findAllPatrons_shouldReturnMappedDTOs() {
        Patron patron = new Patron("Jane", "Smith", "jane@example.com");
        PatronDTO dto = new PatronDTO("Jane", "Smith", "jane@example.com");

        when(patronRepository.findAll()).thenReturn(List.of(patron));
        when(patronMapper.apply(patron)).thenReturn(dto);

        List<PatronDTO> result = patronService.findAll();

        assertEquals("Size < or > 1",1, result.size());
        assertEquals("Jane not first in line","Jane",
                result.getFirst().firstName());
    }

    @Test
    void findById_shouldReturnDTO_whenPatronExists() {
        Patron patron = new Patron("Jane", "Smith", "jane@example.com");
        PatronDTO dto = new PatronDTO("Jane", "Smith", "jane@example.com");

        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(patronMapper.apply(patron)).thenReturn(dto);

        Optional<PatronDTO> result = patronService.findById(1L);

        assertTrue("Patron is missing", result.isPresent());
        assertEquals("Patron is incorrect","jane@example.com",
                result.get().email());
    }

    @Test
    void findById_shouldReturnEmpty_whenPatronNotFound() {
        when(patronRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<PatronDTO> result = patronService.findById(99L);

        assertTrue("Result has vale when needing to be empty",result.isEmpty());
    }

    @Test
    void save_shouldPersistAndReturnDTO() {
        PatronDTO inputDTO = new PatronDTO("John", "Doe", "john@example.com");
        Patron savedPatron = new Patron("John", "Doe", "jane@example.com");
        PatronDTO outputDTO = new PatronDTO("John", "Doe", "john@example.com");

        when(patronRepository.save(any(Patron.class))).thenReturn(savedPatron);
        when(patronMapper.apply(savedPatron)).thenReturn(outputDTO);

        PatronDTO result = patronService.save(inputDTO);

        assertEquals("Result not saved","John", result.firstName());
        verify(patronRepository, times(1)).save(any(Patron.class));
    }
}
