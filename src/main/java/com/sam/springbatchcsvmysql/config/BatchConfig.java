package com.sam.springbatchcsvmysql.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.sam.springbatchcsvmysql.dao.EmployeeDao;
import com.sam.springbatchcsvmysql.entities.Customer;
import com.sam.springbatchcsvmysql.model.Employee;
import com.sam.springbatchcsvmysql.repository.CustomerRepository;

/**
 * @author sumit
 *
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public CustomerRepository customerRepository;

	@Autowired
	public EmployeeDao employeeDao;

	@Bean
	public Job job() {
		return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer())
				.listener(new com.sam.springbatchcsvmysql.step.jpa.Listener(customerRepository)).flow(step1()).end()
				.build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<Customer, Customer>chunk(2)
				.reader(com.sam.springbatchcsvmysql.step.jpa.Reader.reader("customer-data.csv"))
				.processor(new com.sam.springbatchcsvmysql.step.jpa.Processor())
				.writer(new com.sam.springbatchcsvmysql.step.jpa.Writer(customerRepository)).build();
	}

	@Bean
	public Job job1() {
		return jobBuilderFactory.get("job1").incrementer(new RunIdIncrementer())
				.listener(new com.sam.springbatchcsvmysql.step.jdbc.Listener(employeeDao)).flow(job1step1()).end()
				.build();
	}

	@Bean
	public Step job1step1() {
		return stepBuilderFactory.get("job1step1").<Employee, Employee>chunk(2)
				.reader(com.sam.springbatchcsvmysql.step.jdbc.Reader.reader("customer-data.csv"))
				.processor(new com.sam.springbatchcsvmysql.step.jdbc.Processor())
				.writer(new com.sam.springbatchcsvmysql.step.jdbc.Writer(employeeDao)).build();
	}

}
