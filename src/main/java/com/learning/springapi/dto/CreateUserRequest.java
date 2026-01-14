package com.learning.springapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CreateUserRequest {

    @Schema(example = "25")
    @NotNull(message = "Age is required")
    private Integer age;

    @Schema(example = "James", description = "Must be a not empty")
    @NotBlank(message = "Name is required")
    private String name;

    @Schema(example = "james@sample.com", description = "Must be a valid email")
    @Email(message = "Email format is invalid")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email must contain a valid domain (e.g. .com)"
    )
    private String email;

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
