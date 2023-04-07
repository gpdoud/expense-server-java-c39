package com.maxtrain.bootcamp.expensesystem.expenseline;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.maxtrain.bootcamp.expensesystem.expense.Expense;
import com.maxtrain.bootcamp.expensesystem.expense.ExpenseRepository;
import com.maxtrain.bootcamp.expensesystem.item.Item;
import com.maxtrain.bootcamp.expensesystem.item.ItemRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/expenselines")
public class ExpenselineController {

	@Autowired
	private ExpenselineRepository explRepo;
	@Autowired
	private ExpenseRepository expRepo;
	@Autowired
	private ItemRepository itemRepo;
	
	@SuppressWarnings("rawtypes")
	private ResponseEntity recalculateExpenseTotal(int expenseId) {
		Optional<Expense> optExpense = expRepo.findById(expenseId);
		if(optExpense.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		Expense expense = optExpense.get();
		// get the expense lines
		Iterable<Expenseline> expenselines = explRepo.findByExpenseId(expenseId);
		// iterate through the expense lines
		expense.setTotal(0);
		for(Expenseline eline : expenselines) {
			Optional<Item> optItem = itemRepo.findById(eline.getItem().getId());
			if(optItem.isEmpty())
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			Item item = optItem.get();
			expense.setTotal(expense.getTotal() + (eline.getQuantity() * item.getPrice()));
		}
		expRepo.save(expense);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Expenseline>> getExpenselines() {
		return new ResponseEntity<Iterable<Expenseline>>(explRepo.findAll(), HttpStatus.OK);
	}
	@GetMapping("{id}")
	public ResponseEntity<Expenseline> getExpenseline(@PathVariable int id) {
		Optional<Expenseline> optExpenseline = explRepo.findById(id);
		if(optExpenseline.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Expenseline>(optExpenseline.get(), HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<Expenseline> postExpenseline(@RequestBody Expenseline expenseline) {
		if(expenseline == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		Expenseline newExpenseline = explRepo.save(expenseline);
		recalculateExpenseTotal(expenseline.getExpense().getId());
		return new ResponseEntity<Expenseline>(newExpenseline, HttpStatus.CREATED);
	}
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putExpenseline(@PathVariable int id, @RequestBody Expenseline expenseline) {
		if(expenseline == null || expenseline.getId() != id)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		explRepo.save(expenseline);
		recalculateExpenseTotal(expenseline.getExpense().getId());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteExpenseline(@PathVariable int id) {
		Optional<Expenseline> optExpenseline = explRepo.findById(id);
		if(optExpenseline.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		Expenseline expenseline = optExpenseline.get();
		explRepo.delete(expenseline);
		recalculateExpenseTotal(expenseline.getExpense().getId());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
