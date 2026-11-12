package com.example.student.mapper;

import com.example.student.dto.*;
import com.example.student.model.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperImplTest {

    private final StudentMapperImpl mapper = new StudentMapperImpl();

    @Test
    void requestToStudent() {
        StudentRequest req = new StudentRequest();
        req.setName("Ivan");
        req.setSurname("Petrov");
        req.setEmail("ivan@mail.ru");
        req.setPhoneNumber("123");

        Student s = mapper.requestToStudent(req);
        assertEquals("Ivan", s.getName());
        assertEquals("Petrov", s.getSurname());
        assertEquals("ivan@mail.ru", s.getEmail());
    }

    @Test
    void studentToResponse() {
        Student s = new Student();
        s.setId(1L);
        s.setName("Ivan");
        s.setSurname("Petrov");

        StudentResponse resp = mapper.studentToResponse(s);
        assertEquals(1L, resp.getId());
        assertEquals("Ivan", resp.getName());
    }

    @Test
    void studentToFullResponse() {
        Student s = new Student();
        s.setId(1L);
        s.setEmail("ivan@mail.ru");

        StudentFullResponse resp = mapper.studentToFullResponse(s);
        assertEquals("ivan@mail.ru", resp.getEmail());
    }

    @Test
    void studentListToResponseList() {
        Student s1 = new Student();
        s1.setId(1L);          // ← ДОБАВИТЬ
        s1.setName("A");
        Student s2 = new Student();
        s2.setId(2L);          // ← ДОБАВИТЬ
        s2.setName("B");

        StudentResponseList list = mapper.studentListToStudentResponseList(List.of(s1, s2));
        assertEquals(2, list.getTotalCount());
        assertEquals(2, list.getResponse().size());
    }
}