package com.theaterapp.patron;

import com.theaterapp.GlobalApiPrefixConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PatronController.class) // Tells WebMVC which class we are testing
@Import(GlobalApiPrefixConfig.class) // imports the global "/api" URL addition
public class PatronControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    PatronService patronService;

    @Test
    @DisplayName("GET patrons should return 200 with list")
    void GET_patrons_shouldReturn200WithList() throws Exception {
        List<PatronDTO> patrons = List.of(
                new PatronDTO("JaneSmith", "jane@example.com")
        );
        when(patronService.findAll()).thenReturn(patrons);

        /*
         * Checks all information is there
         * AND no password field exists
         */
        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userName").value("JaneSmith"))
                .andExpect(jsonPath("$[0].email").value("jane@example.com"))
                .andExpect(jsonPath("$[0].password").doesNotExist());
    }


    @Test
    @DisplayName("GET patrons returns 404 when not found")
    void GET_patrons_byId_shouldReturn404_whenNotFound() throws Exception {
        when(patronService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/patrons/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST patrons should return 201")
    void POST_patrons_shouldReturn201() throws Exception {
        PatronDTO dto = new PatronDTO("JaneSmith", "jane@example.com");
        when(patronService.save(any())).thenReturn(dto);


        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"userName":"JaneSmith","email":"jane@example.com"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("jane@example.com"));
    }
}
