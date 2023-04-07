package com.maxtrain.bootcamp.expensesystem.expense;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.maxtrain.bootcamp.expensesystem.employee.Employee;

import jakarta.persistence.*;

@Entity
@Table(name="expenses")
public class Expense {
	
	public static final String STATUS_NEW = "NEW";
	public static final String STATUS_REVIEW = "REVIEW";
	public static final String STATUS_APPROVED = "APPROVED";
	public static final String STATUS_REJECTED = "REJECTED";
	public static final String STATUS_PAID = "PAID";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(length=80, nullable=false)
	private String description;
	@Column(length=10, nullable=false)
	private String status;
	@Column(columnDefinition = "decimal(11,2) not null default 0")
	private double total;
	
	@JsonManagedReference
	@ManyToOne(optional = false)
	@JoinColumn(name = "employeeId")
	private Employee employee;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	

}
