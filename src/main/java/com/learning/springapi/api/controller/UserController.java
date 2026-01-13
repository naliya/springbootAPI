package com.learning.springapi.api.controller;

import com.learning.springapi.api.model.User;
import com.learning.springapi.dto.CreateUserRequest;
import com.learning.springapi.dto.PagedResponse;
import com.learning.springapi.dto.UpdateUserRequest;
import com.learning.springapi.dto.UserResponse;
import com.learning.springapi.response.ApiResponse;
import com.learning.springapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;
    public record DeleteResponse(String message) {}

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "Create a new user")
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

    @Operation(summary = "Update a user")
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

    @Operation(summary = "Get user by ID")
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
    @Operation(summary = "Get all user / Filter user")
    @GetMapping("/users")
    public ApiResponse<PagedResponse<UserResponse>> listUsers(
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    )  {
        Page<User> page = userService.searchUsers(minAge, name, email, pageable);

        List<UserResponse> users = page.getContent().stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getAge(),
                        u.getName(),
                        u.getEmail()
                ))
                .toList();

        PagedResponse<UserResponse> response =
                new PagedResponse<>(
                        users,
                        page.getNumber(),
                        page.getSize(),
                        page.getTotalElements(),
                        page.getTotalPages(),
                        page.isFirst(),
                        page.isLast()
                );

        return new ApiResponse<>(200, "Users retrieved successfully", response);
    }

    //Delete User@Operation(summary = "Delete user by ID")
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
