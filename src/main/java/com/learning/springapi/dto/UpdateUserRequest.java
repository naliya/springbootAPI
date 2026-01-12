package com.learning.springapi.dto;

public class UpdateUserRequest {
    private Integer age;
    private String name;
    private String email;

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
