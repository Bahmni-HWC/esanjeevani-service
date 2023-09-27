package org.bahmnihwc.esanjeevaniservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import java.time.Instant;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Value("${jwt.cert_file}")
    private String jwtCertificateSource;

    @Value("${jwt.audience}")
    private String jwtAudience;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/esanjeevani-bridge")
                         .authenticated().anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer
                                .jwt(jwt -> jwt.decoder(jwtDecoder()))
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwtCertificateSource).build();

        jwtDecoder.setJwtValidator(createValidator());

        return jwtDecoder;
    }

    private OAuth2TokenValidator<Jwt> createValidator() {
        return new OAuth2TokenValidator<Jwt>() {
            @Override
            public OAuth2TokenValidatorResult validate(Jwt jwt) {
                // Validate the audience
                if (!jwt.getAudience().contains(jwtAudience)) {
                    OAuth2Error audienceError = new OAuth2Error(
                            OAuth2ErrorCodes.INVALID_TOKEN,
                            "The token is not intended for this application",
                            null);
                    logger.error("Audience validation failed: {}", audienceError.getDescription());
                    return OAuth2TokenValidatorResult.failure(audienceError);
                }

                // Validate the expiration
                if (jwt.getExpiresAt() != null && jwt.getExpiresAt().isBefore(Instant.now())) {
                    OAuth2Error expirationError = new OAuth2Error(
                            OAuth2ErrorCodes.INVALID_TOKEN,
                            "The token has expired",
                            null);
                    logger.error("Token expiration validation failed: {}", expirationError.getDescription());
                    return OAuth2TokenValidatorResult.failure(expirationError);
                }

                // Both audience and expiration are valid
                return OAuth2TokenValidatorResult.success();
            }
        };
    }

}

