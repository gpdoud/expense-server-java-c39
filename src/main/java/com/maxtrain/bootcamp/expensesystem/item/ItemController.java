package com.maxtrain.bootcamp.expensesystem.item;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/items")
public class ItemController {
	
	@Autowired
	private ItemRepository itemRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Item>> GetItems() {
		return new ResponseEntity<Iterable<Item>>(itemRepo.findAll(), HttpStatus.OK);
	}
	@GetMapping("{id}")
	public ResponseEntity<Item> GetItem(@PathVariable int id) {
		Optional<Item> optItem = itemRepo.findById(id);
		if(optItem.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Item>(optItem.get(), HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<Item> PostItem(@RequestBody Item item) {
		if(item == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		Item newItem = itemRepo.save(item);
		return new ResponseEntity<Item>(newItem, HttpStatus.CREATED);
	}
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity PutItem(@PathVariable int id, @RequestBody Item item) {
		if(item == null || item.getId() != id)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		itemRepo.save(item);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity DeleteItem(@PathVariable int id) {
		Optional<Item> optItem = itemRepo.findById(id);
		if(optItem.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		itemRepo.delete(optItem.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
