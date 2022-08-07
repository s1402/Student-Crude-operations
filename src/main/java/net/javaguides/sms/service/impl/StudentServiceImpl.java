package net.javaguides.sms.service.impl;

import java.util.List;
import java.util.regex.Pattern;

import net.javaguides.sms.error.EmailAlreadyExists;
import net.javaguides.sms.error.InvalidEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;
import net.javaguides.sms.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public Student saveStudent(Student student) throws InvalidEmailException, EmailAlreadyExists {
		student.setEmail(student.getEmail().trim());
		if(!validateEmail(student.getEmail())){
			throw new InvalidEmailException("Email Format Is Invalid");
		}

		if(studentRepository.findByEmail(student.getEmail())!=null){
			throw new EmailAlreadyExists("Student with this Email Already exists!" );
		}
		return studentRepository.save(student);
	}

	@Override
	public Student getStudentById(Long id) {
		return studentRepository.findById(id).get();
	}

	@Override
	public Student updateStudent(Student updatedStudent, Long id) throws InvalidEmailException, EmailAlreadyExists {
		// get updatedStudent from database by id
		Student existingStudent = getStudentById(id);
		String updatedEmail = updatedStudent.getEmail();
		if(!validateEmail(updatedEmail)){
			throw new InvalidEmailException("Failed to update ! Email Format Is Invalid");
		}
		//check If Same Email Already Exists In Table Mapped with Some other student
		if(!updatedEmail.equals(existingStudent.getEmail()) &&
				studentRepository.findByEmail(updatedStudent.getEmail()) !=null){
			throw new EmailAlreadyExists("Email Already Exists In Table Mapped with Some other student");
		}
		existingStudent.setId(id);
		existingStudent.setEmail(updatedEmail.trim());
		existingStudent.setFirstName(updatedStudent.getFirstName());
		existingStudent.setLastName(updatedStudent.getLastName());
		return studentRepository.save(existingStudent);
	}

	@Override
	public void deleteStudentById(Long id) {
		studentRepository.deleteById(id);	
	}

	private boolean validateEmail(String email){
		String regexPattern = "^(.+)@(\\S+)$";
		return Pattern.compile(regexPattern)
				.matcher(email)
				.matches();
	}
}
