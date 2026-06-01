package com.code.theaterapp.event;

import com.code.theaterapp.event.dtos.CreateEventDTO;
import com.code.theaterapp.event.dtos.EventDTO;
import com.code.theaterapp.stage.Stage;
import com.code.theaterapp.stage.StageRepo;
import com.code.theaterapp.stage.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;
    private final StageRepo stageRepo;
    private final EventMapper eventMapper;

    public List<EventDTO> getAllEvents() {
        return eventRepo.findAll().stream()
                .map(eventMapper::apply)
                .toList();
    }

    public Optional<EventDTO> getEventById(UUID id) {
        return eventRepo.findById(id).map(eventMapper::apply);
    }

    public EventDTO createEvent(CreateEventDTO createEventDTO) {

        Stage stage = stageRepo.findByIdAndVenueId(createEventDTO.stageId(), createEventDTO.venueId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stage not found"));

        Event event = new Event();
        event.setTitle(createEventDTO.title());
        event.setShowTime(createEventDTO.showTime());
        event.setStage(stage);
        event.setEventCreated(Instant.now());

        Event savedEvent = eventRepo.save(event);
        return eventMapper.apply(savedEvent);

    }


}
