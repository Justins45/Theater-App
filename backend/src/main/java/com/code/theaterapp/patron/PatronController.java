package com.code.theaterapp.patron;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/patron")
public class PatronController {

    @GetMapping("/")
    public String getPatron() {
        return "Welcome to the patron root path";
    }
    @GetMapping("/add")
    public String getAdd() {
        return "Welcome to the patron add path";
    }
}
