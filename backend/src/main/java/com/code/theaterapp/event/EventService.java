package com.code.theaterapp.event;

import com.code.theaterapp.event.dtos.EventDTO;
import jakarta.transaction.Transactional;
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

    @Transactional
    public EventDTO createEvent(Event eventDetails) {
        Event event = new Event();
        event.setTitle(eventDetails.getTitle());
        event.setShowTime(eventDetails.getShowTime());
        event.setEventCreated(Instant.now());

        Event savedEvent = eventRepo.save(event);
        return eventMapper.apply(savedEvent);

    }


}
