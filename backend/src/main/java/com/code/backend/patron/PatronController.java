package com.code.backend.patron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PatronController {

    @Autowired
    PatronRepository patronRepository;

    @GetMapping("/patrons")
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @GetMapping("/patrons/{id}")
    public Optional<Patron> getPatronById(@PathVariable Long id) {
        return getAllPatrons().stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    @PostMapping("/patrons")
    public Patron createPatron(@RequestBody Patron patron) {
        return patronRepository.save(patron);
    }


}
