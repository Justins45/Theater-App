package com.code.theaterapp.patron;

import com.code.theaterapp.shared.enums.Role;
import com.code.theaterapp.shared.person.Person;
import jakarta.persistence.*;
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
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Converts DB role to Role Enum
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "person_id")
    private Person person;

}
