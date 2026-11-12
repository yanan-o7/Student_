package com.example.student.service.impl;

import com.example.student.dto.*;
import com.example.student.exception.EntityNotFoundException;
import com.example.student.mapper.StudentMapper;
import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock private StudentRepository studentRepository;
    @Mock private StudentMapper studentMapper;
    @InjectMocks private StudentServiceImpl studentService;

    @Test
    void testSave() {
        when(studentMapper.requestToStudent(any())).thenReturn(new Student());
        when(studentRepository.save(any())).thenReturn(new Student());
        when(studentMapper.studentToResponse(any())).thenReturn(new StudentResponse());
        assertNotNull(studentService.save(new StudentRequest()));
    }

    @Test
    void testFindById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student()));
        when(studentMapper.studentToFullResponse(any())).thenReturn(new StudentFullResponse());
        assertNotNull(studentService.findById(1L));
    }

    @Test
    void testFindById_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> studentService.findById(1L));
    }

    @Test
    void testFindAll() {
        when(studentRepository.findAll()).thenReturn(List.of(new Student()));
        when(studentMapper.studentListToStudentResponseList(any()))
                .thenReturn(new StudentResponseList(List.of(), 0));
        assertNotNull(studentService.findAll());
    }

    @Test
    void testUpdate() {
        Student student = new Student();
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any())).thenReturn(student);

        StudentUpdateRequest req = new StudentUpdateRequest();
        req.setEmail("test@mail.ru");
        req.setPhoneNumber("123");

        studentService.update(1L, req);
        assertEquals("test@mail.ru", student.getEmail());
    }

    @Test
    void testDelete() {
        studentService.deleteById(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }
}