package com.sam.springbatchcsvmysql.step.jdbc;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import com.sam.springbatchcsvmysql.dao.IemployeeDao;
import com.sam.springbatchcsvmysql.entities.Employee;

/**
 * @author sumit
 *
 */
public class Listener extends JobExecutionListenerSupport {
	private static final Logger log = LoggerFactory.getLogger(Listener.class);

	private final IemployeeDao employeeDao;

	public Listener(IemployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("Finish Job! Check the results");

			List<Employee> employees = employeeDao.loadAllEmployees();

			for (Employee employee : employees) {
				log.info("Found <" + employee + "> in the database.");
			}
		}
	}
}
