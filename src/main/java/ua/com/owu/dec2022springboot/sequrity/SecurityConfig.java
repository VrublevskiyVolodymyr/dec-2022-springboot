package ua.com.owu.dec2022springboot.sequrity;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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
import ua.com.owu.dec2022springboot.sequrity.filters.JWTFilter;

import java.util.Arrays;

@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private JWTFilter jwtFilter;

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers(HttpMethod.POST, "/users/save", "/users/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/users").permitAll()
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
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @SneakyThrows
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList("GET", HttpMethod.POST.name()));
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200"));
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
