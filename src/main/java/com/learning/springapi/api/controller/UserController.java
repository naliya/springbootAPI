package com.learning.springapi.api.controller;

import com.learning.springapi.api.model.User;
import com.learning.springapi.dto.CreateUserRequest;
import com.learning.springapi.dto.PagedResponse;
import com.learning.springapi.dto.UpdateUserRequest;
import com.learning.springapi.dto.UserResponse;
import com.learning.springapi.response.ApiResult;
import com.learning.springapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
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
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<ApiResult<User>> createUser(
            @Valid @RequestBody CreateUserRequest req
    ) {
        User user = userService.createUser(req.getAge(), req.getName(), req.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResult<>(
                        HttpStatus.CREATED.value(),
                        "User created successfully",
                        user
                ));
    }

    @Operation(summary = "Update a user")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = com.learning.springapi.response.CreateUserSuccessDoc.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Request failed (validation or duplicate email)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResult<User>> updateUser(
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
                new ApiResult<>(
                        HttpStatus.OK.value(),
                        "User updated successfully",
                        updated
                )
        );
    }

    @Operation(summary = "Get user by ID")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResult<User>> getUser(@PathVariable Integer id) {
        User user = userService.getUser(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"
                ));

        return ResponseEntity.ok(
                new ApiResult<>(
                        HttpStatus.OK.value(),
                        "User retrieved successfully",
                        user
                )
        );
    }

    //Get All user
    @Operation(
            summary = "Get users",
            description = "Retrieve users with pagination and optional filters"
    )
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/users")
    public ApiResult<PagedResponse<UserResponse>> listUsers(
            @Parameter(description = "Minimum age (inclusive)")
            @RequestParam(required = false) Integer minAge,

            @Parameter(description = "Search by name (contains)")
            @RequestParam(required = false) String name,

            @Parameter(description = "Search by email (contains)")
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

        return new ApiResult<>(200, "Users retrieved successfully", response);
    }

    // Delete User
    @Operation(summary = "Delete user by ID")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/users/{id}")
   public ResponseEntity<ApiResult<Void>> deleteUser(@PathVariable Integer id) {
       userService.deleteUser(id);
       return ResponseEntity.ok(
               new ApiResult<>(
                       HttpStatus.OK.value(),
                       "User deleted successfully",
                       null
               )
       );
   }

}
