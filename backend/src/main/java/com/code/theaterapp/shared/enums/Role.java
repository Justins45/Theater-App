package com.code.theaterapp.shared.enums;

/**
 * Defines the available user roles for Spring Security access control.
 * The {@code ROLE_} prefix is required by Spring Security's {@link SimpleGrantedAuthority}.
 */
public enum Role {
    ROLE_ADMIN,
    ROLE_STAFF,
    ROLE_PATRON
}
