package com.example.student.service.impl;

import com.example.student.dto.user.UserRequest;
import com.example.student.dto.user.UserResponse;
import com.example.student.exception.EmailAlreadyExistsException;
import com.example.student.exception.EntityNotFoundException;
import com.example.student.mapper.UserMapper;
import com.example.student.model.User;
import com.example.student.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private UserServiceImpl userService;

    @Test
    void existsByEmail() {
        when(userRepository.existsByEmail("a@mail.ru")).thenReturn(true);
        assertTrue(userService.existsByEmail("a@mail.ru"));
    }

    @Test
    void register_WhenEmailExists_ReturnsFalse() {
        UserRequest req = new UserRequest();
        req.setEmail("a@mail.ru");
        User u = new User();
        u.setEmail("a@mail.ru");

        when(userMapper.toEntity(req)).thenReturn(u);
        when(userRepository.existsByEmail("a@mail.ru")).thenReturn(true);
        assertFalse(userService.register(req));
    }

    @Test
    void register_WhenNew_Saves() {
        UserRequest req = new UserRequest();
        req.setEmail("b@mail.ru");
        req.setPassword("pass");
        User u = new User();
        u.setEmail("b@mail.ru");
        u.setPassword("pass");

        when(userMapper.toEntity(req)).thenReturn(u);
        when(userRepository.existsByEmail("b@mail.ru")).thenReturn(false);
        when(passwordEncoder.encode("pass")).thenReturn("hashed");

        assertTrue(userService.register(req));
        verify(userRepository).save(u);
    }

    @Test
    void save_WhenEmailExists_Throws() {
        UserRequest req = new UserRequest();
        req.setEmail("a@mail.ru");
        when(userRepository.existsByEmail("a@mail.ru")).thenReturn(true);
        assertThrows(EmailAlreadyExistsException.class, () -> userService.save(req));
    }

    @Test
    void save_WhenNew_ReturnsResponse() {
        UserRequest req = new UserRequest();
        req.setEmail("b@mail.ru");
        req.setPassword("pass");

        when(userRepository.existsByEmail("b@mail.ru")).thenReturn(false);
        when(passwordEncoder.encode("pass")).thenReturn("hashed");
        when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(userMapper.toResponse(any())).thenReturn(new UserResponse());

        assertNotNull(userService.save(req));
    }

    @Test
    void findByEmail_WhenExists() {
        when(userRepository.findByEmail("a@mail.ru")).thenReturn(Optional.of(new User()));
        assertNotNull(userService.findByEmail("a@mail.ru"));
    }

    @Test
    void findByEmail_WhenNotExists_Throws() {
        when(userRepository.findByEmail("x@mail.ru")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.findByEmail("x@mail.ru"));
    }

    @Test
    void deleteById() {
        userService.deleteById(1L);
        verify(userRepository).deleteById(1L);
    }
}
