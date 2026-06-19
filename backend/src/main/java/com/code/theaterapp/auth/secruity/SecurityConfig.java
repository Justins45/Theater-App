package com.code.theaterapp.auth.secruity;


import com.code.theaterapp.patron.PatronDetailsService;
import com.code.theaterapp.staff.StaffDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final StaffDetailsService staffDetailsService;
    private final PatronDetailsService patronDetailsService;
    private final JwtFilter jwtFilter;

    @Value("${frontend.url}")
    private String frontEndURL;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Qualifier("staffAuthProvider")
    public AuthenticationProvider staffAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(staffDetailsService);

        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    @Bean
    @Qualifier("staffAuthManager")
    public AuthenticationManager staffAuthenticationManager(
            @Qualifier("staffAuthProvider") AuthenticationProvider staffProvider) {
        return new ProviderManager(List.of(staffProvider));
    }

    @Bean
    @Qualifier("patronAuthProvider")
    public AuthenticationProvider patronAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(patronDetailsService);

        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    @Bean
    @Primary
    @Qualifier("patronAuthManager")
    public AuthenticationManager patronAuthenticationManager(
            @Qualifier("patronAuthProvider") AuthenticationProvider patronProvider) {
        return new ProviderManager(List.of(patronProvider));
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(frontEndURL));
        config.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Content-Type"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L); // set age of preflight for 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Defines the role hierarchy: ADMIN → STAFF → CUSTOMER.
     * <ul>
     *   <li>Note: Spring Security automatically prepends the {@code ROLE_} prefix,
     *   so even though roles are stored as {@code ROLE_ADMIN} in the DB, do not include
     *   the prefix here to avoid duplication</li>
     * </ul>
     */
    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("ADMIN").implies("STAFF")
                .role("STAFF").implies("PATRON")
                .build();
    }

    /**
     * Configures the main security filter chain for the application.
     * <ul>
     *   <li>Disables CSRF (stateless JWT auth makes it unnecessary)</li>
     *   <li>Defines role-based access rules for admin and staff endpoints</li>
     *   <li>Registers the {@link JwtFilter} to validate tokens before Spring's auth filter</li>
     *   <li>Sets session policy to stateless since auth state lives in cookies, not the server</li>
     * </ul>
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            @Qualifier("staffAuthProvider") AuthenticationProvider staffProvider,
            @Qualifier("patronAuthProvider") AuthenticationProvider patronProvider
    ) {

        // Keep separate like this to allow for comment addition

        // TODO: Add CSRF configuration when frontend origin is known
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.authenticationProvider(staffProvider);
        http.authenticationProvider(patronProvider);

        http.authorizeHttpRequests(request -> request
                .requestMatchers("/api/staff/login").permitAll()
                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/staff/**").hasAuthority("ROLE_STAFF")
                .requestMatchers("/api/auth/me").hasAuthority("ROLE_PATRON")

                // All other endpoints are public — lock these down as the app grows
                .anyRequest().permitAll()
        );

        // NOTE: Frontend should redirect 401 (not authenticated) → login page
        //       and 403 (authenticated but forbidden) → access denied page

        http.httpBasic(Customizer.withDefaults());


        // Stateless: server holds no session, JWT cookie is the source of truth
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Intercept requests with our JWT filter before Spring's built-in credential filter
        http.addFilterBefore(jwtFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}

