package com.code.theaterapp.auth.secruity.accounts;


import com.code.theaterapp.patron.Patron;
import com.code.theaterapp.shared.person.Person;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class PatronAccount implements UserDetails {

    private final Person person;
    private final Patron patron;

    public PatronAccount(Person person, Patron patron) {
        this.person = person;
        this.patron = patron;
    }

    @Override
    public @NullMarked Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(patron.getRole().toString()));
    }

    @Override
    public @Nullable String getPassword() {
        return person.getPassword();
    }

    @Override
    public @NullMarked String getUsername() {
        return person.getUsername();
    }


    // TODO: impl later ex: account banning / email verification (locking account till verified or something like that)

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

