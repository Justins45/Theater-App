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
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID personId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private OffsetDateTime accountCreated;

    private String firstName;
    private String lastName;
    private String displayName;

    /**
     * Returns the best available display name for the user.
     * <ul>
     *   <li>Returns display name if is it present</li>
     *   <li>Or returns first name if no display name is set</li>
     *   <li>Falls back to null if no display name is set, and no first name is set</li>
     * </ul>
     */
    public String getAccountName() {
        if(!displayName.isBlank()) {
            return getDisplayName();
        }
        if (!firstName.isBlank()) {
            return getFirstName();
        }
        return null;
    }

}
