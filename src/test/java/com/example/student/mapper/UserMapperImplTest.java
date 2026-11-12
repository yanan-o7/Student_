package com.example.student.mapper;

import com.example.student.dto.user.*;
import com.example.student.model.Role;
import com.example.student.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperImplTest {

    private final UserMapperImpl mapper = new UserMapperImpl();

    @Test
    void toResponse() {
        User u = new User();
        u.setId(1L);
        u.setEmail("test@mail.ru");
        u.setRoles(Set.of(Role.ADMIN));

        UserResponse resp = mapper.toResponse(u);
        assertEquals(1L, resp.getId());
        assertEquals("test@mail.ru", resp.getEmail());
    }

    @Test
    void toEntity() {
        UserRequest req = new UserRequest();
        req.setEmail("new@mail.ru");
        req.setPassword("pass");
        req.setRoles(Set.of(Role.TEACHER));

        User u = mapper.toEntity(req);
        assertEquals("new@mail.ru", u.getEmail());
    }

    @Test
    void toListResponse() {
        User u1 = new User();
        u1.setId(1L);          // ← ДОБАВИТЬ
        u1.setEmail("a@mail.ru");
        User u2 = new User();
        u2.setId(2L);          // ← ДОБАВИТЬ
        u2.setEmail("b@mail.ru");

        UserResponseList list = mapper.toListResponse(List.of(u1, u2));
        assertEquals(2, list.getTotalCount());
        assertEquals(2, list.getResponses().size());
    }
}