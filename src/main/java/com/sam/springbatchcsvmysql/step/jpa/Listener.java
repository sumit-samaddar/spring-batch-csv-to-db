package com.sam.springbatchcsvmysql.step.jpa;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import com.sam.springbatchcsvmysql.entities.Customer;
import com.sam.springbatchcsvmysql.repository.CustomerRepository;

public class Listener extends JobExecutionListenerSupport {
	private static final Logger log = LoggerFactory.getLogger(Listener.class);

	private final CustomerRepository customerRepository;

	public Listener(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("Finish Job! Check the results");

			List<Customer> customers = customerRepository.findAll();

			for (Customer customer : customers) {
				log.info("Found <" + customer + "> in the database.");
			}
		}
	}
}
