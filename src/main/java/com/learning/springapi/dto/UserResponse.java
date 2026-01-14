package com.learning.springapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserResponse {

    @Schema(example = "1")
    private Integer id;
    @Schema(example = "James")
    private String name;
    @Schema(example = "25")
    private Integer age;
    @Schema(example = "james@sample.com")
    private String email;

    public UserResponse(Integer id, Integer age, String name, String email) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.email = email;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public Integer getAge() { return age; }
    public String getEmail() { return email; }
}



