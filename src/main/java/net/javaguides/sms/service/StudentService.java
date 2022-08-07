package net.javaguides.sms.service;

import java.util.List;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.error.EmailAlreadyExists;
import net.javaguides.sms.error.InvalidEmailException;

public interface StudentService {
	List<Student> getAllStudents();
	
	Student saveStudent(Student student) throws InvalidEmailException, EmailAlreadyExists;
	
	Student getStudentById(Long id);
	
	Student updateStudent(Student student, Long id) throws InvalidEmailException, EmailAlreadyExists;
	
	void deleteStudentById(Long id);
}
