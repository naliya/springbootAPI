package com.learning.springapi.api.controller;

import com.learning.springapi.api.model.User;
import com.learning.springapi.dto.CreateUserRequest;
import com.learning.springapi.dto.UpdateUserRequest;
import com.learning.springapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class UserController {

    private UserService userService;
    public record DeleteResponse(String message) {}

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    //Create User
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody CreateUserRequest req) {
        return userService.createUser(
                req.getAge(),
                req.getName(),
                req.getEmail()
        );
    }

    //Update User
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Integer id,
                           @RequestBody UpdateUserRequest req) {
        return userService.updateUser(id, req.getAge(), req.getName(), req.getEmail());
    }

    //Get user by ID
    @GetMapping("/user")
    public User getUser(@RequestParam Integer id) {
        return userService.getUser(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));
    }

    //Get All user
    @GetMapping("/users")
    public java.util.List<User> listUsers() {
        return userService.getAllUsers();
    }

   //Delete User
    @DeleteMapping("/users/{id}")
    public DeleteResponse deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return new DeleteResponse("User deleted successfully");
    }

}
