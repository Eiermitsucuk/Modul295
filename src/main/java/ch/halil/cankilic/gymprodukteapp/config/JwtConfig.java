package ch.halil.cankilic.gymprodukteapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class JwtConfig {

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = List.of();
            // Check if the JWT contains the "realm_access" claim
            if (jwt.getClaims().containsKey("realm_access")) {
                var realmAccess = jwt.getClaimAsMap("realm_access");
                if (realmAccess.containsKey("roles")) {
                    authorities = ((List<String>) realmAccess.get("roles")).stream()
                            // Prefix roles with "ROLE_" and convert to uppercase
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                            .collect(Collectors.toList());
                }
            }
            return authorities;
        });
        return converter;
    }
}
