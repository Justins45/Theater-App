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

    public List<Event> getAllEvents() {
        return eventRepo.findAll().stream()
                .map(EventDTO::from)
                .toList();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepo.findById(id);
    }

    @Transactional
    public EventDTO createEvent(Event eventDetails) {
        Event event = new Event();
        event.setTitle(eventDetails.getTitle());
        event.setShowTime(eventDetails.getShowTime());
        event.setEventCreated(Instant.now());

        Event savedEvent = eventRepo.save(event);

        // TODO: make mapper
        return new EventDTO(
                savedEvent.getTitle(),
                savedEvent.getWallClock()
        );
        
    }


}
