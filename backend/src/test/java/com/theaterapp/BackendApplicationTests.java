package com.theaterapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import javax.sql.DataSource;

// webEnvironment = MOCK prevents Spring from trying to start a real server
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class BackendApplicationTests {

    // Mocking these specific beans will "trick" Spring into thinking a
    // database is connected, resolving the ApplicationContext error.
    @MockitoBean
    private DataSource dataSource;

    @MockitoBean
    private EntityManagerFactory entityManagerFactory;

    @Test
    void contextLoads() {
        // Just checking if the context can start without crashing
    }
}