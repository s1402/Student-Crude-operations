package net.javaguides.sms.service;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.error.EmailAlreadyExists;
import net.javaguides.sms.error.InvalidEmailException;
import net.javaguides.sms.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() {
        Student student = Student.builder().id(1L).email("d@k.com").firstName("Dinesh").lastName("Kartik").build();
        List<Student> students = Collections.singletonList(student);
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student));
        Mockito.when(studentRepository.save(student)).thenReturn(student);
        Mockito.when(studentRepository.findAll()).thenReturn(students);
        Mockito.when(studentRepository.findByEmail("d@k.com")).thenReturn(student);
    }

    @Test
    @DisplayName("Test get All the Users ")
    public void GetAllStudents() {
        assertEquals(studentService.getAllStudents().size(),1);
    }

    @Test
    public void testFindStudentById() {
        Long id = 1L;
        Student student = studentService.getStudentById(id);
        assertEquals(student.getId(),id);
    }

    @Test
    public void testUpdateStudentThrowsInvalidEmailException() {
        Student student = Student.builder().id(1L).email("invalidEmail").firstName("Dinesh").lastName("Kartik").build();
        Exception exception = assertThrows(InvalidEmailException.class, () -> {
            studentService.updateStudent(student,1L);
        });

        String expectedMessage = "Failed to update ! Email Format Is Invalid";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    public void testUpdateStudentForValidDetails() throws EmailAlreadyExists, InvalidEmailException{
        Student student = Student.builder().id(2L).email("s@r.com").firstName("shivam").lastName("Ranjan").build();
        Student updatedStudent = studentService.updateStudent(student,1L);
        assertEquals(updatedStudent.getFirstName(),student.getFirstName());
        assertEquals(updatedStudent.getLastName(),student.getLastName());
        assertEquals(updatedStudent.getEmail(),student.getEmail());
    }

    @Test
    public void testSaveNewStudentThrowsInvalidEmailException()  {
        Student student = Student.builder().id(1L).email("invalidEmail").firstName("Dinesh").lastName("Kartik").build();
        Exception exception = assertThrows(InvalidEmailException.class, () -> {
            studentService.saveStudent(student);
        });

        String expectedMessage = "Email Format Is Invalid";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    public void testSaveNewStudentThrowsEmailAlreadyExistsException()  {
        Student student = Student.builder().id(1L).email("d@k.com").firstName("Dinesh").lastName("Kartik").build();
        Exception exception = assertThrows(EmailAlreadyExists.class, () -> {
            studentService.saveStudent(student);
        });

        String expectedMessage = "Student with this Email Already exists!";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    public void testDeleteStudentById() {
        long studentId = 1L;
        willDoNothing().given(studentRepository).deleteById(studentId);

        // when -  action or the behaviour that we are going test
        studentService.deleteStudentById(studentId);

        // then - verify the output
        verify(studentRepository, times(1)).deleteById(studentId);
    }
}