package com.learning.springapi.security;

import com.learning.springapi.response.ApiResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthService authService;

    public AuthController(AuthenticationManager authManager, JwtService jwtService,
                          UserDetailsService userDetailsService, AuthService authService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.authService = authService;
    }

    public record RegisterRequest(
            @NotBlank String username,
            @NotBlank @Size(min = 8) String password
    ) {}

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<Void> register(@Valid @RequestBody RegisterRequest req) {
        authService.register(req.username(), req.password());
        return new ApiResult<>(201, "User registered successfully", null);
    }

    public record LoginRequest(@NotBlank String username, @NotBlank String password) {}
    public record LoginResponse(String token) {}

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password())
        );

        var userDetails = userDetailsService.loadUserByUsername(req.username());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .toList();

        String token = jwtService.generateToken(req.username(), roles);
        return new LoginResponse(token);
    }

}

