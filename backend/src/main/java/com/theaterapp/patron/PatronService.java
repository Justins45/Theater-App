package com.theaterapp.patron;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatronService {

    // ALL BUSINESS LOGIC

    private final PatronRepository patronRepository;
    private final PatronMapper patronMapper;
    private final PasswordEncoder passwordEncoder;

    public List<PatronDTO> findAll() {
        return patronRepository.findAll().stream()
                .map(patronMapper::apply)
                .toList();
    }

    public PatronDTO save(PatronRegisterDTO patronRegisterDTO) {
        Patron patron = new Patron(patronRegisterDTO.userName(),
                passwordEncoder.encode(patronRegisterDTO.password()),
                patronRegisterDTO.email(),
                patronRegisterDTO.role());
        return patronMapper.apply(patronRepository.save(patron));
    }

    public Optional<PatronDTO> findById(Long id) {
        return patronRepository.findById(id)
                .map(patronMapper::apply);
    }
}
