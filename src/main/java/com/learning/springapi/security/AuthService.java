package com.learning.springapi.security;

import com.learning.springapi.repository.AuthUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final AuthUserRepository repo;
    private final PasswordEncoder encoder;

    public AuthService(AuthUserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public void register(String username, String password) {
        if (repo.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        AuthUser user = new AuthUser(username, encoder.encode(password), "USER");
        repo.save(user);
    }
}

