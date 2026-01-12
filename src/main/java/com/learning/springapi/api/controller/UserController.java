package com.learning.springapi.api.controller;

import com.learning.springapi.api.model.User;
import com.learning.springapi.dto.CreateUserRequest;
import com.learning.springapi.dto.UpdateUserRequest;
import com.learning.springapi.response.ApiResponse;
import com.learning.springapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

import java.util.List;
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
    public ResponseEntity<ApiResponse<User>> createUser(
            @Valid @RequestBody CreateUserRequest req
    ) {
        User user = userService.createUser(req.getAge(), req.getName(), req.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        HttpStatus.CREATED.value(),
                        "User created successfully",
                        user
                ));
    }

    //Update User
    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateUserRequest req
    ) {
        User updated = userService.updateUser(
                id,
                req.getAge(),
                req.getName(),
                req.getEmail()
        );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "User updated successfully",
                        updated
                )
        );
    }

    //Get user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable Integer id) {
        User user = userService.getUser(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"
                ));

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "User retrieved successfully",
                        user
                )
        );
    }

    //Get All user
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> listUsers() {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Users retrieved successfully",
                        userService.getAllUsers()
                )
        );
    }

   //Delete User
   @DeleteMapping("/users/{id}")
   public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Integer id) {
       userService.deleteUser(id);

       return ResponseEntity.ok(
               new ApiResponse<>(
                       HttpStatus.OK.value(),
                       "User deleted successfully",
                       null
               )
       );
   }

}
