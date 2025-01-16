// config/SecurityConfig.java
package conversion_divisas_prueba.conversion_divisas_prueba.config;

import conversion_divisas_prueba.conversion_divisas_prueba.security.ApiKeyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String API_KEY = "mi-clave-api-segura-123";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/conversion").permitAll()
                .anyRequest().permitAll())
            .addFilterBefore(apiKeyFilter(), UsernamePasswordAuthenticationFilter.class)
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable());
        
        return http.build();
    }

    @Bean
    public ApiKeyFilter apiKeyFilter() {
        return new ApiKeyFilter(API_KEY);
    }
}
