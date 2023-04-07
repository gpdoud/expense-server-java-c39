package com.maxtrain.bootcamp.expensesystem.expenseline;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/expenselines")
public class ExpenselineController {

	@Autowired
	private ExpenselineRepository explRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Expenseline>> GetExpenselines() {
		return new ResponseEntity<Iterable<Expenseline>>(explRepo.findAll(), HttpStatus.OK);
	}
	@GetMapping("{id}")
	public ResponseEntity<Expenseline> GetExpenseline(@PathVariable int id) {
		Optional<Expenseline> optExpenseline = explRepo.findById(id);
		if(optExpenseline.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Expenseline>(optExpenseline.get(), HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<Expenseline> PostExpenseline(@RequestBody Expenseline expenseline) {
		if(expenseline == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		Expenseline newExpenseline = explRepo.save(expenseline);
		return new ResponseEntity<Expenseline>(newExpenseline, HttpStatus.CREATED);
	}
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity PutExpenseline(@PathVariable int id, @RequestBody Expenseline expenseline) {
		if(expenseline == null || expenseline.getId() != id)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		explRepo.save(expenseline);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity DeleteExpenseline(@PathVariable int id) {
		Optional<Expenseline> optExpenseline = explRepo.findById(id);
		if(optExpenseline.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		explRepo.delete(optExpenseline.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
