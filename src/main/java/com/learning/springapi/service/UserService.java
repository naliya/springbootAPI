package com.learning.springapi.service;

import com.learning.springapi.api.model.User;
import com.learning.springapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

    public List<User> getAllUsers() {
        return repo.findAll();
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
}


//import com.learning.springapi.api.model.User;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.concurrent.atomic.AtomicInteger;
//
//@Service
//public class UserService {
//
//    private final List<User> userList = new ArrayList<>();
//    private final AtomicInteger idGen;
//
//    public UserService() {
//        userList.add(new User(1, 29, "Liam", "liam@sample.com"));
//        userList.add(new User(2, 32, "Jonathan", "jonathan@sample.com"));
//        userList.add(new User(3, 36, "Raymond", "raymond@sample.com"));
//        userList.add(new User(4, 28, "Daniel", "daniel@sample.com"));
//
//        int maxId = userList.stream()
//                .map(User::getId)
//                .max(Integer::compareTo)
//                .orElse(0);
//
//        this.idGen = new AtomicInteger(maxId + 1);
//    }
//
//    // Get user by ID
//    public Optional<User> getUser(Integer id) {
//        return userList.stream()
//                .filter(user -> id.equals(user.getId()))
//                .findFirst();
//    }
//
//    // Get all users
//    public List<User> getAllUsers() {
//        return userList;
//    }
//
//    // Create user
//    public User createUser(Integer age, String name, String email) {
//        Integer id = idGen.getAndIncrement();
//        User user = new User(id, age, name, email);
//        userList.add(user);
//        return user;
//    }
//
//    // Update User
//    public User updateUser(Integer id, Integer age, String name, String email) {
//        User user = getUser(id).orElseThrow(() ->
//                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
//        );
//
//        // update fields
//        user.setAge(age);
//        user.setName(name);
//        user.setEmail(email);
//
//        return user;
//    }
//
//    // Delete User
//    public void deleteUser(Integer id) {
//        boolean removed = userList.removeIf(user -> id.equals(user.getId()));
//
//        if (!removed) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND,
//                    "User not found"
//            );
//        }
//    }
//}
