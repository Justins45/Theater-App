package com.code.theaterapp.staff;

import com.code.theaterapp.shared.enums.Role;
import com.code.theaterapp.shared.person.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "staff")
public class Staff {

    @Id
    private UUID id;

    @Column(nullable = false)
    private OffsetDateTime staffAccountCreation;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Converts DB role to Role Enum
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "person_id")
    private Person person;

}

