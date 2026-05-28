package com.code.theaterapp.venue;

import com.code.theaterapp.venue.dtos.VenueDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/venue")
public class VenueController {

    private final VenueService venueService;

    @GetMapping
    public List<VenueDTO> getAllVenues() { return venueService.getAllVenues(); }

    @GetMapping("/{id}")
    public Optional<VenueDTO> getVenue(@PathVariable("id") Long id) {
        return venueService.getVenueById(id);
    }

}
