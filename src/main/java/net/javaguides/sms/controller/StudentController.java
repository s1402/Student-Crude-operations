package net.javaguides.sms.controller;

import net.javaguides.sms.error.EmailAlreadyExists;
import net.javaguides.sms.error.InvalidEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.service.StudentService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	// handler method to handle list students and return mode and view
	@GetMapping("/students")
	public String listStudents(Model model) {
		model.addAttribute("students", studentService.getAllStudents());
		return "students";
	}
	
	@GetMapping("/students/new")
	public String createStudentForm(Model model) {
		Student student = new Student();
		model.addAttribute("student", student);
		return "create_student";
		
	}
	
	@PostMapping("/students")
	public String saveStudent(@ModelAttribute("student") Student student , RedirectAttributes redirectAttributes) throws EmailAlreadyExists, InvalidEmailException {
		try {
			studentService.saveStudent(student);
		} catch (Exception exception) {
			redirectAttributes.addFlashAttribute("invalid_message", exception.getMessage());
			return "redirect:/students?failure";
		}
		return "redirect:/students";
	}
	
	@GetMapping("/students/edit/{id}")
	public String editStudentForm(@PathVariable Long id, Model model) {
		model.addAttribute("student", studentService.getStudentById(id));
		return "edit_student";
	}

	@PostMapping("/students/{id}")
	public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student ,
	RedirectAttributes redirectAttributes) {

		try {
			studentService.updateStudent(student,id);
		} catch (Exception exception) {
			redirectAttributes.addFlashAttribute("invalid_message", exception.getMessage());
			return "redirect:/students?failure";
		}

		return "redirect:/students";		
	}
	
	// handler method to handle delete student request
	@GetMapping("/students/{id}")
	public String deleteStudent(@PathVariable Long id) {
		studentService.deleteStudentById(id);
		return "redirect:/students";
	}
	
}
