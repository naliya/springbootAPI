package com.learning.springapi.config;

import com.learning.springapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.learning.springapi.api.model.User;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository repo;

    public DataSeeder(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        if (repo.count() == 0) {
            repo.save(new User(null, 29, "Liam", "liam@sample.com"));
            repo.save(new User(null, 32, "Jonathan", "jonathan@sample.com"));
            repo.save(new User(null, 36, "Raymond", "raymond@sample.com"));
            repo.save(new User(null, 28, "Daniel", "daniel@sample.com"));
        }
    }
}

