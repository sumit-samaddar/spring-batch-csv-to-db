package com.sam.springbatchcsvmysql.dao;

import java.util.List;
import com.sam.springbatchcsvmysql.model.Employee;

/**
 * @author sumit
 *
 */
public interface EmployeeDao {
	public void insert(List<? extends Employee> employees);

	List<Employee> loadAllEmployees();
}
