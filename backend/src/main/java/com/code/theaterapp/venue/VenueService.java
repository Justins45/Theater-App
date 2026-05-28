package com.code.theaterapp.venue;

import com.code.theaterapp.venue.dtos.CreateVenueDTO;
import com.code.theaterapp.venue.dtos.VenueDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
