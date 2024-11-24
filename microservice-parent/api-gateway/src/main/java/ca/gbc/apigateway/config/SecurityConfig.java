package ca.gbc.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*
    * Configure the security filter chain for HTTP requests.
    *
    * This configuration requires all incoming HTTP requests to be authenticated.
    * It also configures OAuth2 resource server support to validate JWT tokens.
    *
    * @param httpSecurity the HttpSecurity to customize
    * @return the configured SecurityFilterChain
    * @throws Exception if an error occurs while configuring security.
    * */

    private final String[] noauthResourceUris = {
            "/swagger-ui",
            "/swagger-ui/*",
            "/v3/api-docs/**",
            "/swagger-resource/**",
            "/api-docs/**",
            "/aggregate/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{




        log.info("Initializing Security Filter Chain...");

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(noauthResourceUris)
                        .permitAll()
                        .anyRequest().authenticated())  // All request require authentication
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()))
                .build();
    }
}
