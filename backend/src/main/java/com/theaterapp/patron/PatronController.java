package com.theaterapp.patron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatronController {

    @Autowired
    PatronRepository patronRepository;

    @GetMapping("/patrons")
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @GetMapping("/patrons/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable Long id) {
        return patronRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/patrons")
    public ResponseEntity<Patron> createPatron(@RequestBody Patron patron) {
        Patron saved = patronRepository.save(patron);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


}
