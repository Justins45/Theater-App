package com.theaterapp.event;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
@Import(GlobalApiPrefixConfig.class)
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    EventService eventService;

    @Test
    @DisplayName("GET Events should return 200 with list")
    void GET_events_shouldReturn200WithList() throws Exception {
        List<EventDTO> events = List.of(
                new EventDTO("spiderman", "spiderman description", "jane " +
                        "doe", 69)
        );
        when(eventService.findAll()).thenReturn(events);

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("spiderman"))
                .andExpect(jsonPath("$[0].description").value("spiderman " +
                        "description"))
                .andExpect(jsonPath("$[0].director").value("jane doe"))
                .andExpect(jsonPath("$[0].capacity").value(69));
    }

    @Test
    @DisplayName("POST events should return 201")
    void POST_events_shouldReturn201() throws Exception {
        EventDTO dto = new EventDTO("spiderman","spiderman description","jane" +
                " doe",69);
        when(eventService.save(any())).thenReturn(dto);


        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"spiderman","description":"spiderman description","director":"jane doe","capacity":69}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.director").value("jane doe"));
    }
}
