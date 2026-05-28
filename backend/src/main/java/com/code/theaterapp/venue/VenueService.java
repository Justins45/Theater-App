package com.code.theaterapp.venue;

import com.code.theaterapp.venue.dtos.VenueDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepo venueRepo;
    private final VenueMapper venueMapper;

    public List<VenueDTO> getAllVenues() {
        return venueRepo.findAll().stream()
                .map(venueMapper::apply)
                .toList();
    }

    public Optional<VenueDTO> getVenueById(Long id) {
        return venueRepo.findById(id).map(venueMapper::apply);
    }
}
