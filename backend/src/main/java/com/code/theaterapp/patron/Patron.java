package com.code.theaterapp.patron;

import java.util.UUID;

import com.code.theaterapp.shared.enums.Role;
import com.code.theaterapp.shared.person.Person;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "patron")
public class Patron {

    @Id
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Converts DB role to Role Enum
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

}
