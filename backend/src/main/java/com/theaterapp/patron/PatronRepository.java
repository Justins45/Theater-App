package com.theaterapp.patron;

import org.springframework.data.jpa.repository.JpaRepository;

// ONLY TALKS WITH DATABASE NOTHING MORE
public interface PatronRepository extends JpaRepository<Patron, Long> {
}
