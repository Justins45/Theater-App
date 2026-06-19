package com.code.theaterapp.event;

import com.code.theaterapp.event.dtos.CreateEventDTO;
import com.code.theaterapp.event.dtos.EventDetailsDTO;
import com.code.theaterapp.event.dtos.EventSummaryDTO;
import com.code.theaterapp.exceptions.EntityNotFoundException;
import com.code.theaterapp.stage.Stage;
import com.code.theaterapp.stage.StageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<EventSummaryDTO> getAllEvents() {
        return eventRepo.findAll().stream()
                .map(eventMapper::toSummary)
                .toList();
    }

    public Optional<EventDetailsDTO> getEventById(UUID id) {
        return eventRepo.findById(id).map(eventMapper::toDetails);
    }

    public EventDetailsDTO createEvent(CreateEventDTO createEventDTO) {

        Stage stage = stageRepo.findByIdAndVenueId(createEventDTO.stageId(), createEventDTO.venueId())
                .orElseThrow(() -> new EntityNotFoundException("Stage not found"));

        Event event = new Event();
        event.setTitle(createEventDTO.title());
        event.setStage(stage);
        event.setEventCreated(Instant.now());

        Event savedEvent = eventRepo.save(event);
        return eventMapper.toDetails(savedEvent);

    }


}
