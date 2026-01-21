package com.learning.springapi.service;

import com.learning.springapi.api.model.User;
import com.learning.springapi.api.spec.UserSpecifications;
import com.learning.springapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public Optional<User> getUser(Integer id) {
        return repo.findById(id);
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public User createUser(Integer age, String name, String email) {
        // optional guard
        if (repo.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        User user = new User(null, age, name, email); // id = null so DB generates it

        log.debug("User saved to DB: id={}", user.getId());

        return repo.save(user);
    }

    public User updateUser(Integer id, Integer age, String name, String email) {
        if (repo.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        User user = repo.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found for update: id={}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                });

        user.setAge(age);
        user.setName(name);
        user.setEmail(email);

        return repo.save(user);
    }

    public void deleteUser(Integer id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        repo.deleteById(id);
    }

    // Filtering
    public Page<User> searchUsers(Integer minAge, String name, String email, Pageable pageable) {

        Specification<User> spec = Specification.where(null);

        if (minAge != null) {
            spec = spec.and(UserSpecifications.ageGte(minAge));
        }
        if (name != null && !name.isBlank()) {
            spec = spec.and(UserSpecifications.nameContainsIgnoreCase(name.trim()));
        }
        if (email != null && !email.isBlank()) {
            spec = spec.and(UserSpecifications.emailContainsIgnoreCase(email.trim()));
        }

        return repo.findAll(spec, pageable);
    }
}