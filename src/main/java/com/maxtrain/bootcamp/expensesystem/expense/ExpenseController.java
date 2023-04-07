package com.maxtrain.bootcamp.expensesystem.expense;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maxtrain.bootcamp.expensesystem.employee.Employee;
import com.maxtrain.bootcamp.expensesystem.employee.EmployeeRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
	
	@Autowired
	private ExpenseRepository expRepo;
	@Autowired
	private EmployeeRepository emplRepo;

	@GetMapping
	public ResponseEntity<Iterable<Expense>> getExpenses() {
		return new ResponseEntity<Iterable<Expense>>(expRepo.findAll(), HttpStatus.OK);
	}
	@GetMapping("{id}")
	public ResponseEntity<Expense> getExpense(@PathVariable int id) {
		Optional<Expense> optExpense = expRepo.findById(id);
		if(optExpense.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Expense>(optExpense.get(), HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<Expense> postExpense(@RequestBody Expense expense) {
		if(expense == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		Expense newExpense = expRepo.save(expense);
		return new ResponseEntity<Expense>(newExpense, HttpStatus.CREATED);
	}
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putExpense(@PathVariable int id, @RequestBody Expense expense) {
		if(expense == null || expense.getId() != id)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		expRepo.save(expense);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@SuppressWarnings("rawtypes")
	@PutMapping("pay/{id}")
	public ResponseEntity payExpense(@PathVariable int id, @RequestBody Expense expense) {
		if(expense == null || expense.getId() != id)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		// read the employee for the expense
		Optional<Employee> optEmployee = emplRepo.findById(expense.getEmployee().getId()); 
		if(optEmployee.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		// get the employee instance
		Employee employee = optEmployee.get();
		// reduce the amount due
		employee.setExpensesDue(employee.getExpensesDue() - expense.getTotal());
		// increase the amount paid
		employee.setExpensesPaid(employee.getExpensesPaid() + expense.getTotal());
		emplRepo.save(employee);
		// set the expense to paid
		expense.setStatus(Expense.STATUS_PAID);
		expRepo.save(expense);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteExpense(@PathVariable int id) {
		Optional<Expense> optExpense = expRepo.findById(id);
		if(optExpense.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		expRepo.delete(optExpense.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
