package com.theaterapp;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@Disabled("Skipping FULL context load becuase GitHub Actions cannot connect " +
//        "to a real database")
@SpringBootTest
class TheaterAppTests {

    @Test
    void contextLoads() {
    }

}
