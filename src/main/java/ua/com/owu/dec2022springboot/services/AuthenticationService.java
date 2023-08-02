package ua.com.owu.dec2022springboot.services;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.owu.dec2022springboot.dao.UserDAO;
import ua.com.owu.dec2022springboot.models.*;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private MailService mailService;


    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = new User();
        user.setFirstname(registerRequest.getFirstname());
        user.setLastname(registerRequest.getLastname());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        user.setRefreshToken(refreshToken);
        userDAO.save(user);

        String email = user.getEmail();
        String body = "Hello user  " + user.getFirstname() + " to activate your account click <a href='http://localhost:8080/users/activate/" + user.getId() + " '>here</a>";
        mailService.sendEmail(email, body, Optional.empty());

        return AuthenticationResponse
                .builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        User user = userDAO.findByEmail(authenticationRequest.getEmail());
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        user.setRefreshToken(refreshToken);

        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse refresh(RequestRefresh requestRefresh) {
        String token = requestRefresh.getRefreshToken();
        String username = jwtService.extractUsername(token);
        User user = userDAO.findByEmail(username);
        String newAccessToken = null;
        String newRefreshToken = null;

        if (user
                .getRefreshToken()
                .equals(token)) {

            newAccessToken = jwtService.generateToken(user);
            newRefreshToken = jwtService.generateRefreshToken(user);
            user.setRefreshToken(newRefreshToken);
            userDAO.save(user);
        }

        return AuthenticationResponse
                .builder()
                .token(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
