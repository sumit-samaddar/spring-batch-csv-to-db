package com.sam.springbatchcsvmysql.step.jpa;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import com.sam.springbatchcsvmysql.entities.Customer;

/**
 * @author sumit
 *
 */
public class Reader {
	public static FlatFileItemReader<Customer> reader(String path) {

		FlatFileItemReader<Customer> reader = new FlatFileItemReader<Customer>();

		// claspath reading
		// reader.setResource(new ClassPathResource(path));
		// external filepath reading
		reader.setResource(new FileSystemResource(path));
		reader.setLineMapper(new DefaultLineMapper<Customer>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "id", "firstName", "lastName" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Customer>() {
					{
						setTargetType(Customer.class);
					}
				});
			}
		});
		return reader;
	}
}
