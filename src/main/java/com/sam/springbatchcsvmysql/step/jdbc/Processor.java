package com.sam.springbatchcsvmysql.step.jdbc;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.sam.springbatchcsvmysql.entities.Employee;

/**
 * @author sumit
 *
 */
public class Processor implements ItemProcessor<Employee, Employee> {

	private static final Logger log = LoggerFactory.getLogger(Processor.class);

	@Override
	public Employee process(Employee employee) throws Exception {
		Random r = new Random();

		final String firstName = employee.getFirstName().toUpperCase();
		final String lastName = employee.getLastName().toUpperCase();

		final Employee fixedEmployee = new Employee(r.nextInt(), firstName, lastName);

		log.info("Converting (" + employee + ") into (" + fixedEmployee + ")");

		return fixedEmployee;
	}
}
