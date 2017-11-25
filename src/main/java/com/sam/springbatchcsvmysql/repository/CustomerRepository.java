package com.sam.springbatchcsvmysql.repository;

import java.util.List;
import com.sam.springbatchcsvmysql.entities.Customer;

/**
 * @author sumit
 *
 */
public interface CustomerRepository {

	List<Customer> findAll();

	public void insert(List<? extends Customer> customers);

}