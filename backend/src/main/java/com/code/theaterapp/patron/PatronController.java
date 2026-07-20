package com.code.theaterapp.patron;

import com.code.theaterapp.auth.secruity.accounts.PatronAccount;
import com.code.theaterapp.patron.dtos.PatronAccountDetails;
import com.code.theaterapp.patron.dtos.PatronDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/patron")
public class PatronController {

    private final PatronService patronService;
    private final PatronMapper patronMapper;

    @GetMapping
    public ResponseEntity<PatronAccountDetails> getPatronInformation(
        @AuthenticationPrincipal PatronAccount principal
    ) {
        return patronService.getPatron(principal);
    }

    



}
