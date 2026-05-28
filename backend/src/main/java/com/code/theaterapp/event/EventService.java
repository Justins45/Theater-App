package com.code.theaterapp.event;

import com.code.theaterapp.event.dtos.CreateEventDTO;
import com.code.theaterapp.event.dtos.EventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;
    private final EventMapper eventMapper;

    public List<EventDTO> getAllEvents() {
        return eventRepo.findAll().stream()
                .map(eventMapper::apply)
                .toList();
    }

    public Optional<EventDTO> getEventById(Long id) {
        return eventRepo.findById(id).map(eventMapper::apply);
    }

    public EventDTO createEvent(CreateEventDTO createEventDTO) {
        Event event = new Event();
        event.setTitle(createEventDTO.title());
        event.setShowTime(createEventDTO.showTime());
        event.setEventCreated(Instant.now());

        Event savedEvent = eventRepo.save(event);
        return eventMapper.apply(savedEvent);

    }


}
