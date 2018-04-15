package com.sam.springbatchcsvmysql.step.jdbc;

import java.util.List;
import org.springframework.batch.item.ItemWriter;
import com.sam.springbatchcsvmysql.dao.IemployeeDao;
import com.sam.springbatchcsvmysql.entities.Employee;

/**
 * @author sumit
 *
 */
public class Writer implements ItemWriter<Employee> {

	private final IemployeeDao employeeDao;

	public Writer(IemployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	@Override
	public void write(List<? extends Employee> employees) throws Exception {
		employeeDao.insert(employees);
	}

}