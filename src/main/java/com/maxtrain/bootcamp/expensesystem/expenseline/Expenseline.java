package com.maxtrain.bootcamp.expensesystem.expenseline;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.maxtrain.bootcamp.expensesystem.expense.Expense;
import com.maxtrain.bootcamp.expensesystem.item.Item;

import jakarta.persistence.*;

@Entity
@Table(name = "expenselines")
public class Expenseline {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int quantity;
	
	@JsonBackReference
	@ManyToOne(optional=false)
	@JoinColumn(name="expenseId")
	private Expense expense;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="itemId")
	private Item item;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Expense getExpense() {
		return expense;
	}

	public void setExpense(Expense expense) {
		this.expense = expense;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	
}
