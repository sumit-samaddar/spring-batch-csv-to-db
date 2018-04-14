package com.sam.springbatchcsvmysql.dao;

import java.util.List;
import com.sam.springbatchcsvmysql.entities.Employee;

/**
 * @author sumit
 *
 */
public interface IemployeeDao {
	
	public void insert(List<? extends Employee> employees);
	List<Employee> loadAllEmployees();
	
}
