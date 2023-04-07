package com.maxtrain.bootcamp.expensesystem.employee;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository emplRepo;

	@GetMapping
	public ResponseEntity<Iterable<Employee>> getEmployees() {
		return new ResponseEntity<Iterable<Employee>>(emplRepo.findAll(), HttpStatus.OK);
	}
	@GetMapping("{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable int id) {
		Optional<Employee> optEmployee = emplRepo.findById(id);
		if(optEmployee.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Employee>(optEmployee.get(), HttpStatus.OK);
	}
	@GetMapping("{email}/{password}")
	public ResponseEntity<Employee> getEmployeeByEmailAndPassword(@PathVariable String email, @PathVariable String password) {
		Optional<Employee> optEmployee = emplRepo.findByEmailAndPassword(email, password);
		if(optEmployee.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Employee>(optEmployee.get(), HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<Employee> postEmployee(@RequestBody Employee employee) {
		if(employee == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		Employee newEmployee = emplRepo.save(employee);
		return new ResponseEntity<Employee>(newEmployee, HttpStatus.CREATED);
	}
	@SuppressWarnings("rawtypes")
	@PutMapping("review/{id}")
	public ResponseEntity putEmployee(@PathVariable int id, @RequestBody Employee employee) {
		if(employee == null || employee.getId() != id)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		emplRepo.save(employee);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteEmployee(@PathVariable int id) {
		Optional<Employee> optEmployee = emplRepo.findById(id);
		if(optEmployee.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		emplRepo.delete(optEmployee.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
