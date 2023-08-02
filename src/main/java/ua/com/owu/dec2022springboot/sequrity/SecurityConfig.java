package ua.com.owu.dec2022springboot.sequrity;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ua.com.owu.dec2022springboot.filters.JWTFilter;

import java.util.Arrays;

@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private JWTFilter jwtFilter;
    private AuthenticationProvider authenticationProvider;

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(managementConfigurer -> managementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers(HttpMethod.POST,"/api/v1/auth/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/users/save", "/users/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/users","/users/activate/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/users/**").hasAnyAuthority("ADMIN", "SUPERADMIN")
                                .requestMatchers(HttpMethod.GET, "/users/**").hasAnyAuthority("ADMIN", "SUPERADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/users/**").hasAnyAuthority("ADMIN", "SUPERADMIN")
                                .requestMatchers(HttpMethod.POST, "/cars").hasAnyAuthority("USER", "ADMIN", "SUPERADMIN")
                                .requestMatchers(HttpMethod.GET, "/cars", "/cars/producer/{producer}", "/cars/power/{value}").hasAnyAuthority("USER", "ADMIN", "SUPERADMIN")
                                .requestMatchers(HttpMethod.GET, "/cars/**").hasAnyAuthority("ADMIN", "SUPERADMIN")
                                .requestMatchers(HttpMethod.GET, "/photo/**").hasAnyAuthority("ADMIN", "SUPERADMIN")
                                .requestMatchers(HttpMethod.GET, "/superadmin/**").hasAuthority("SUPERADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/cars/**").hasAnyAuthority("ADMIN", "SUPERADMIN")
                                .requestMatchers(HttpMethod.POST, "/cars/**").hasAnyAuthority("ADMIN", "SUPERADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/cars/**").hasAnyAuthority("ADMIN", "SUPERADMIN"))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
