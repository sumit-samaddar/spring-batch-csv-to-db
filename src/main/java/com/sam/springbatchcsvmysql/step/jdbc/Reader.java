package com.sam.springbatchcsvmysql.step.jdbc;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import com.sam.springbatchcsvmysql.entities.Employee;

/**
 * @author sumit
 *
 */
public class Reader {
	public static FlatFileItemReader<Employee> reader(String path) {

		FlatFileItemReader<Employee> reader = new FlatFileItemReader<Employee>();

		//claspath reading
		//reader.setResource(new ClassPathResource(path));
		//external filepath reading
		reader.setResource(new FileSystemResource(path));
		reader.setLineMapper(new DefaultLineMapper<Employee>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "id", "firstName", "lastName" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {
					{
						setTargetType(Employee.class);
					}
				});
			}
		});
		return reader;
	}
}
