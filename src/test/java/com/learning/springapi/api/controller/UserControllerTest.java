package com.learning.springapi.api.controller;


import com.learning.springapi.api.model.User;
import com.learning.springapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

//    @Test
//    void listUsers_shouldReturn3UsersPerPage() throws Exception {
//        // Arrange: fake page with 3 users
//        List<User> users = List.of(
//                new User(1, 29, "Liam", "liam@sample.com"),
//                new User(2, 32, "Jonathan", "jonathan@sample.com"),
//                new User(3, 36, "Raymond", "raymond@sample.com")
//        );
//
//        Pageable pageable = PageRequest.of(0, 3, Sort.by("id").descending());
//        Page<User> page = new PageImpl<>(users, pageable, 8); // totalElements=8
//
//        Mockito.when(userService.getAllUsers(any(Pageable.class)))
//                .thenReturn(page);
//
//        // Act + Assert
//        mockMvc.perform(get("/users")
//                        .param("page", "0")
//                        .param("size", "3")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                // ApiResponse wrapper fields (adjust if your names differ)
//                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.message").value("Users retrieved successfully"))
//
//                // PagedResponse fields
//                .andExpect(jsonPath("$.data.content.length()").value(3))
//                .andExpect(jsonPath("$.data.page").value(0))
//                .andExpect(jsonPath("$.data.size").value(3))
//                .andExpect(jsonPath("$.data.totalElements").value(8))
//                .andExpect(jsonPath("$.data.totalPages").value(3))
//
//                // check first user fields
//                .andExpect(jsonPath("$.data.content[0].id").value(1))
//                .andExpect(jsonPath("$.data.content[0].name").value("Liam"));
//    }

//    @Test
//    void createUser_invalidBody_shouldReturn400() throws Exception {
//
//        String body = """
//                {
//                    "name":"Jay",
//                    "age":"22",
//                    "email":""
//                }
//                """;
//
//        mockMvc.perform(post("/users")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(body))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$.status").value(400))
//                .andExpect(jsonPath("$.errors.name").exists())
//                .andExpect(jsonPath("$.errors.email").exists());
//                verifyNoInteractions(userService);
//    }

//    @Test
//    void getUserById_invalidBody_shouldReturn400() throws Exception {
//
//        String body = """
//                {
//                    "name":"";
//                    "age":"";
//                    "email":"";
//                }
//                """;
//
//        mockMvc.perform(post("/users/")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$status").value(400))
//                .andExpect(jsonPath("$.error.name").exists())
//                .andExpect(jsonPath("$.errors.email").exists());
//        verifyNoInteractions(userService);
//
//    }
}

