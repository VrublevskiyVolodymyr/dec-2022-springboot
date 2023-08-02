package ua.com.owu.dec2022springboot.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.owu.dec2022springboot.models.AuthenticationRequest;
import ua.com.owu.dec2022springboot.models.AuthenticationResponse;
import ua.com.owu.dec2022springboot.models.RegisterRequest;
import ua.com.owu.dec2022springboot.models.RequestRefresh;
import ua.com.owu.dec2022springboot.services.AuthenticationService;

@Controller
@RequestMapping(value = "/api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse>  authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody RequestRefresh requestRefresh) {

        return ResponseEntity.ok(authenticationService.refresh(requestRefresh));
    }
}
