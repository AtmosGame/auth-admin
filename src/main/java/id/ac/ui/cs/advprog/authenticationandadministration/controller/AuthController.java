package id.ac.ui.cs.advprog.authenticationandadministration.controller;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.AuthenticationRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.AuthenticationResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.RegisterRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.RegisterResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register (
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login (
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
