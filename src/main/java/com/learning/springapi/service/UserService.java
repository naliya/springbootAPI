package com.learning.springapi.service;

import com.learning.springapi.api.model.User;
import com.learning.springapi.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Service
public class UserService {

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
        return repo.save(user);
    }

    public User updateUser(Integer id, Integer age, String name, String email) {
        if (repo.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        User user = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

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

        boolean hasAge = (minAge != null);
        boolean hasName = (name != null && !name.isBlank());
        boolean hasEmail = (email != null && !email.isBlank());

        if (hasAge && hasName && hasEmail) {
            return repo.findByAgeGreaterThanEqualAndNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
                    minAge, name, email, pageable
            );
        }
        if (hasAge && hasName) {
            return repo.findByAgeGreaterThanEqualAndNameContainingIgnoreCase(minAge, name, pageable);
        }
        if (hasAge && hasEmail) {
            return repo.findByAgeGreaterThanEqualAndEmailContainingIgnoreCase(minAge, email, pageable);
        }
        if (hasName && hasEmail) {
            return repo.findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(name, email, pageable);
        }
        if (hasAge) {
            return repo.findByAgeGreaterThanEqual(minAge, pageable);
        }
        if (hasName) {
            return repo.findByNameContainingIgnoreCase(name, pageable);
        }
        if (hasEmail) {
            return repo.findByEmailContainingIgnoreCase(email, pageable);
        }

        return repo.findAll(pageable);
    }
}