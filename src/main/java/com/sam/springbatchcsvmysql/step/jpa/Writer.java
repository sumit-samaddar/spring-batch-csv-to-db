package com.sam.springbatchcsvmysql.step.jpa;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import com.sam.springbatchcsvmysql.entities.Customer;
import com.sam.springbatchcsvmysql.repository.CustomerRepository;

public class Writer implements ItemWriter<Customer> {
	
	private final CustomerRepository customerRepository;

	public Writer(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public void write(List<? extends Customer> customers) throws Exception {
		customerRepository.insert(customers);
	}

}
