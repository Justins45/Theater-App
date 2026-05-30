package com.code.theaterapp.shared.person;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "person", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class Person {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID personId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private OffsetDateTime accountCreated;

    private String firstName;
    private String lastName;


    /**
     * Returns the best available display name for the user.
     * <ul>
     *   <li>Returns first name if is it present</li>
     *   <li>Falls back to username if no name is set</li>
     * </ul>
     */
    public String getDisplayName() {
        if (firstName != null && !firstName.isBlank()) {
            return firstName;
        }
        return username;
    }

}
