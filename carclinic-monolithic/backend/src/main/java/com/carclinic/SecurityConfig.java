package com.carclinic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:5173"));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    config.setExposedHeaders(List.of("Location"));
    config.setAllowCredentials(false);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  public JwtDecoder jwtDecoder(
      @Value("${app.keycloak.jwk-set-uri}") String jwkSetUri,
      @Value("${app.keycloak.issuer-uri}") String issuerUri) {
    NimbusJwtDecoder decoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    decoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(issuerUri));
    return decoder;
  }

  @Bean
  public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(this::extractRealmRoles);
    return converter;
  }

  @Bean
  public SecurityFilterChain filterChain(
      HttpSecurity http,
      Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter) throws Exception {
    http
        .csrf((csrf) -> csrf.ignoringRequestMatchers(
            "/api/**",
            "/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/error"))
        .cors(withDefaults())
        .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers("/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/error").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/staff/**").hasAnyRole("admin", "staff")
            .requestMatchers("/api/staff/**").hasRole("admin")
            .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("admin", "staff")
            .requestMatchers("/api/**").hasAnyRole("admin", "staff")
            .anyRequest().authenticated())
        .oauth2ResourceServer((oauth2) -> oauth2.jwt((jwt) -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));

    return http.build();
  }

  private Collection<GrantedAuthority> extractRealmRoles(Jwt jwt) {
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    Map<String, Object> realmAccess = jwt.getClaim("realm_access");
    if (realmAccess == null) {
      return authorities;
    }

    Object roles = realmAccess.get("roles");
    if (roles instanceof Collection<?> roleNames) {
      roleNames.stream()
          .filter(String.class::isInstance)
          .map(String.class::cast)
          .map((role) -> new SimpleGrantedAuthority("ROLE_" + role))
          .forEach(authorities::add);
    }

    return authorities;
  }
}
