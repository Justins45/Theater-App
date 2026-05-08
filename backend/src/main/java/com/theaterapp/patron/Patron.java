package com.theaterapp.patron;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "patrons")
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String role;

    private String password;

    public Patron() {
    }

    public Patron(String userName, String password, String email, String role) {
        this.userName = userName;
        this.password = password;
        this.email = email;

        if (role == null || role.isEmpty()) {
            this.role = "CUSTOMER";
        } else {
            this.role = role;
        }
    }

}
