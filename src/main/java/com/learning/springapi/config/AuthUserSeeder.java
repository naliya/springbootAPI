package com.learning.springapi.config;

import com.learning.springapi.repository.AuthUserRepository;
import com.learning.springapi.security.AuthUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthUserSeeder implements CommandLineRunner {

    private final AuthUserRepository repo;
    private final PasswordEncoder encoder;

    public AuthUserSeeder(AuthUserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (!repo.existsByUsername("admin")) {
            repo.save(new AuthUser("admin", encoder.encode("admin123"), "ADMIN"));
        }
        if (!repo.existsByUsername("user")) {
            repo.save(new AuthUser("user", encoder.encode("user123"), "USER"));
        }
    }
}

