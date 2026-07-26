package com.code.theaterapp.patron;

import com.code.theaterapp.auth.secruity.accounts.PatronAccount;
import com.code.theaterapp.patron.dtos.PatronAccountDetails;
import com.code.theaterapp.patron.dtos.PatronDetailsDTO;
import com.code.theaterapp.patron.dtos.PatronNamingPatch;
import com.code.theaterapp.patron.dtos.PatronNamingRemovalPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/patron")
public class PatronController {

    private final PatronService patronService;

    @GetMapping
    public ResponseEntity<PatronAccountDetails> getPatronInformation(
        @AuthenticationPrincipal PatronAccount principal
    ) {
        return patronService.getPatron(principal);
    }

    @PatchMapping("/update-naming")
    public ResponseEntity<PatronDetailsDTO> updatePatronNaming(
            @AuthenticationPrincipal PatronAccount patronAccount,
            @RequestBody PatronNamingPatch patronNamingPatch
    ) {
        return patronService.updatePatronNaming(patronAccount, patronNamingPatch);
    }

    @PatchMapping("/remove-naming")
    public ResponseEntity<PatronDetailsDTO> updatePatronNaming(
            @AuthenticationPrincipal PatronAccount patronAccount,
            @RequestBody PatronNamingRemovalPatch patronNamingRemovalPatch
    ) {
        return patronService.removePatronNaming(patronAccount, patronNamingRemovalPatch);
    }


}
