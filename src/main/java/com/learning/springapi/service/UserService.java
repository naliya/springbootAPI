package com.learning.springapi.service;

import com.learning.springapi.api.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

//@Service
//public class UserService {
//
//    private List<User> userList;
//    private final AtomicInteger idGen = new AtomicInteger(5);// since hardcoded value is 1-4
//
//    public UserService(){
//        userList = new ArrayList<>();
//
//        User user1 = new User(1, 29,"Liam", "liam@sample.com");
//        User user2 = new User(2, 32, "Jonathan", "jonathan@sample.com");
//        User user3 = new User(3, 36, "Raymond", "raymond@sample.com");
//        User user4 = new User(4, 28, "Daniel", "daniel@sample.com");
//
//        int maxId = userList.stream()
//                .map(User::getId)
//                .max(Integer::compareTo)
//                .orElse(0);
//
//        this.idGen = new AtomicInteger(maxId + 1);
//
//        userList.addAll(Arrays.asList(user1, user2, user3, user4 ));
//    }
//
//    // get user by ID
//    public Optional<User> getUser(Integer id) {
//        Optional<User> optional = Optional.empty();
//        for (User user: userList) {
//            if(id == user.getId()){
//                optional = Optional.of(user);
//                return optional;
//            }
//        }
//        return optional;
//    }
//
//    // Get all user
//    public List<User> getAllUsers() {
//        return userList;
//    }
//
//    // Create User
//    public User createUser(Integer age, String name, String email) {
//        Integer id = idGen.getAndIncrement();
//        User user = new User(id, age, name, email);
//        userList.add(user);
//        return user;
//    }

@Service
public class UserService {

    private final List<User> userList = new ArrayList<>();
    private final AtomicInteger idGen;

    public UserService() {
        userList.add(new User(1, 29, "Liam", "liam@sample.com"));
        userList.add(new User(2, 32, "Jonathan", "jonathan@sample.com"));
        userList.add(new User(3, 36, "Raymond", "raymond@sample.com"));
        userList.add(new User(4, 28, "Daniel", "daniel@sample.com"));

        int maxId = userList.stream()
                .map(User::getId)
                .max(Integer::compareTo)
                .orElse(0);

        this.idGen = new AtomicInteger(maxId + 1);
    }

    // Get user by ID
    public Optional<User> getUser(Integer id) {
        return userList.stream()
                .filter(user -> id.equals(user.getId()))
                .findFirst();
    }

    // Get all users
    public List<User> getAllUsers() {
        return userList;
    }

    // Create user
    public User createUser(Integer age, String name, String email) {
        Integer id = idGen.getAndIncrement();
        User user = new User(id, age, name, email);
        userList.add(user);
        return user;
    }
}
