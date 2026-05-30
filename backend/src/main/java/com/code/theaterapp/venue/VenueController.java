package com.code.theaterapp.venue;

import com.code.theaterapp.venue.dtos.CreateVenueDTO;
import com.code.theaterapp.venue.dtos.VenueDTO;
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
    public List<VenueDTO> getAllVenues() { return venueService.getAllVenues(); }

    @GetMapping("/{id}")
    public VenueDTO getVenue(@PathVariable("id") Integer id) {
        return venueService.getVenueById(id);
    }

    @PostMapping
    public ResponseEntity<VenueDTO> createVenue(@RequestBody CreateVenueDTO request) {
        VenueDTO dto = venueService.createVenue(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

}
