package com.learning.springapi.response;

import com.learning.springapi.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

// This exists ONLY for Swagger schema rendering.
public class CreateUserSuccessDoc extends ApiResult<UserResponse> {
    public CreateUserSuccessDoc() { super(201, "User created successfully", null); }
}

//public class CreateUserFailDoc extends ResponseStatusException<"400", "Email already exists"> {
//    public CreateUserFailDoc() { super(400, "Email already exists", null); }
//}

