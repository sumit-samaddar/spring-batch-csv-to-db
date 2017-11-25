package com.sam.springbatchcsvmysql.step.jdbc;

import java.util.List;
import org.springframework.batch.item.ItemWriter;
import com.sam.springbatchcsvmysql.dao.EmployeeDao;
import com.sam.springbatchcsvmysql.model.Employee;


/**
 * @author sumit
 *
 */
public class Writer implements ItemWriter<Employee> {

	private final EmployeeDao employeeDao;

	public Writer(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	@Override
	public void write(List<? extends Employee> employees) throws Exception {
		employeeDao.insert(employees);
	}

}