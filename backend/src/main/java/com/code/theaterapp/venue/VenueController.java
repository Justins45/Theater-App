package com.code.theaterapp.venue;

import com.code.theaterapp.venue.dtos.CreateVenueDTO;
import com.code.theaterapp.venue.dtos.VenueDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/venue")
public class VenueController {

    private final VenueService venueService;

    @GetMapping
    public List<VenueDetailsDTO> getAllVenues() { return venueService.getAllVenues(); }

    @GetMapping("/{id}")
    public VenueDetailsDTO getVenue(@PathVariable("id") Integer id) {
        return venueService.getVenueById(id);
    }

    @PostMapping
    public ResponseEntity<VenueDetailsDTO> createVenue(@RequestBody CreateVenueDTO request) {
        VenueDetailsDTO dto = venueService.createVenue(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

}
