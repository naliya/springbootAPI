package com.learning.springapi.api.spec;

import com.learning.springapi.api.model.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<User> ageGte(Integer minAge) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("age"), minAge);
    }

    public static Specification<User> nameContainsIgnoreCase(String name) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<User> emailContainsIgnoreCase(String email) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }
}

