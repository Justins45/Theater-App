package com.code.theaterapp.venue;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Entity
@Getter
@Setter
@Service
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "venue" )
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String timeZone;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private Instant dateCreated;

    public String getAddress() {
        return getStreet() + " "
                + getCity() + " "
                + getProvince() + " "
                + getPostalCode() + " "
                + getCountry();
    }
}
