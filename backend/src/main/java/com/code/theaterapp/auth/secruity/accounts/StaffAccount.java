package com.code.theaterapp.auth.secruity.accounts;

import com.code.theaterapp.shared.enums.Role;
import com.code.theaterapp.shared.person.Person;
import com.code.theaterapp.staff.Staff;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;

public class StaffAccount implements UserDetails {

    private final Person person;
    private final Staff staff;

    public StaffAccount(Person person, Staff staff) {
        this.person = person;
        this.staff = staff;
    }

    // Patron accounts will ALWAYS be ROLE_PATRON
    @Override
    public @NullMarked Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(staff.getRole().toString()));
    }

    @Override
    public @Nullable String getPassword() {
        return person.getPassword();
    }

    /**
     * We return EMAIL because we do not use username
     */
    @Override
    public @NullMarked String getUsername() {
        return getEmail();
    }

    public String getEmail() { return person.getEmail(); }
    public String getFirstname() { return person.getFirstName(); }
    public String getLastname() { return person.getLastName(); }
    public String getDisplayName() { return person.getDisplayName(); }
    public Role getRole() { return staff.getRole(); }
    public OffsetDateTime getAccountCreation() { return staff.getStaffAccountCreation(); }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
