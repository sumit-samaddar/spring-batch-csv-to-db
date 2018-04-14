package com.sam.springbatchcsvmysql;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.sam.springbatchcsvmysql.dao.IemployeeDao;
import com.sam.springbatchcsvmysql.entities.Customer;
import com.sam.springbatchcsvmysql.entities.Employee;
import com.sam.springbatchcsvmysql.repository.ICustomerRepository;

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
	public ICustomerRepository customerRepository;

	@Autowired
	public IemployeeDao employeeDao;

	@Bean
	public Job customerJob() {
		return jobBuilderFactory.get("customerJob").incrementer(new RunIdIncrementer())
				.listener(new com.sam.springbatchcsvmysql.step.jpa.Listener(customerRepository)).flow(customerJobStep())
				.end().build();
	}

	@Bean
	public Step customerJobStep() {
		return stepBuilderFactory.get("customerJobStep").<Customer, Customer>chunk(2)
				.reader(com.sam.springbatchcsvmysql.step.jpa.Reader.reader("customer-data.csv"))
				.processor(new com.sam.springbatchcsvmysql.step.jpa.Processor())
				.writer(new com.sam.springbatchcsvmysql.step.jpa.Writer(customerRepository)).build();
	}

	@Bean
	public Job employeeJob() {
		return jobBuilderFactory.get("employeeJob").incrementer(new RunIdIncrementer())
				.listener(new com.sam.springbatchcsvmysql.step.jdbc.Listener(employeeDao)).flow(employeeJobStep()).end()
				.build();
	}

	@Bean
	public Step employeeJobStep() {
		return stepBuilderFactory.get("employeeJobStep").<Employee, Employee>chunk(2)
				.reader(com.sam.springbatchcsvmysql.step.jdbc.Reader.reader("customer-data.csv"))
				.processor(new com.sam.springbatchcsvmysql.step.jdbc.Processor())
				.writer(new com.sam.springbatchcsvmysql.step.jdbc.Writer(employeeDao)).build();
	}

}
