package com.sam.springbatchcsvmysql.model;

/**
 * @author sumit
 *
 */
public class Employee {
	private long id;
	private String firstName;
	private String lastName;

	public Employee() {
	}

	public Employee(long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return String.format("Employee[id=%d , firstName='%s', lastName='%s']", id, firstName, lastName);
	}
}