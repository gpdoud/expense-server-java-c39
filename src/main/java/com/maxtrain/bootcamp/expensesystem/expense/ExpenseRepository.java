package com.maxtrain.bootcamp.expensesystem.expense;

import org.springframework.data.repository.CrudRepository;

public interface ExpenseRepository extends CrudRepository<Expense, Integer> {
	Iterable<Expense> findByStatus(String status);
}
