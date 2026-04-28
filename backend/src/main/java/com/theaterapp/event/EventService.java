package com.theaterapp.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;


    public List<EventDTO> findAll() {
        return eventRepository.findAll().stream()
                .map(eventMapper::apply)
                .toList();
    }

    public EventDTO save(EventDTO eventDTO) {
        Event event = new Event(
                eventDTO.title(),
                eventDTO.description(),
                eventDTO.director(),
                eventDTO.capacity()
        );
        return eventMapper.apply(eventRepository.save(event));
    }
}
