package com.theaterapp.patron;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/patrons")
@RequiredArgsConstructor
public class PatronController {

    private final PatronService patronService;


    @GetMapping()
    public List<PatronDTO> getAllPatrons() {
        return patronService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<PatronDTO> getPatronById(@PathVariable Long id) {
        return patronService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<PatronDTO> createPatron(@RequestBody PatronRegisterDTO patronRegisterDTO) {
        PatronDTO saved = patronService.save(patronRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


}
