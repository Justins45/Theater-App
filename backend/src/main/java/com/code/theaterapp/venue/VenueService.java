package com.code.theaterapp.venue;

import com.code.theaterapp.venue.dtos.CreateVenueDTO;
import com.code.theaterapp.venue.dtos.VenueDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

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

    public VenueDTO getVenueById(Integer id) {
        Venue venue = venueRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stage not found"));

        return venueMapper.apply(venue);
    }

    public VenueDTO createVenue(CreateVenueDTO createVenueDTO) {
        Venue venue = new Venue();
        venue.setName(createVenueDTO.name());
        venue.setTimeZone(createVenueDTO.timeZone());
        venue.setStreet(createVenueDTO.street());
        venue.setCity(createVenueDTO.city());
        venue.setProvince(createVenueDTO.province());
        venue.setPostalCode(createVenueDTO.postalCode());
        venue.setCountry(createVenueDTO.country());
        venue.setDateCreated(Instant.now());

        Venue savedVenue = venueRepo.save(venue);
        return venueMapper.apply(savedVenue);
    }
}
