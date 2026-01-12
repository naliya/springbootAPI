package com.learning.springapi.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer age;
    private String name;
    private String email;

    public User() {} // JPA needs this

    public User(Integer id, Integer age, String name, String email) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.email = email;
    }

    public Integer getId() { return id; }
    public Integer getAge() { return age; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public void setId(Integer id) { this.id = id; }
    public void setAge(Integer age) { this.age = age; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}


//public class User {
//    private int id;
//    private int age;
//    private String name;
//    private String email;
//
//    public User(int id, int age, String name, String email) {
//        this.id = id;
//        this.age = age;
//        this.name = name;
//        this.email = email;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//}