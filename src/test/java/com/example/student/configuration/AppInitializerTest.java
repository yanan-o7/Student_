package com.example.student.configuration;

import com.example.student.repository.StudentRepository;
import com.example.student.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AppInitializerTest {

    @Test
    void testInit() {
        UserRepository userRepository = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        StudentRepository studentRepository = mock(StudentRepository.class);
        
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(studentRepository.count()).thenReturn(0L);

        AppInitializer initializer = new AppInitializer(userRepository, passwordEncoder, studentRepository);
        initializer.init();

        verify(userRepository, times(3)).save(any());
        verify(studentRepository, times(5)).save(any());
    }
}