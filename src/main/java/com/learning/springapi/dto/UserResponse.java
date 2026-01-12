package com.learning.springapi.dto;

public class UserResponse {

    private Integer id;
    private String name;
    private Integer age;
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



