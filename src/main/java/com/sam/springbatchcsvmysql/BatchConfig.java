package com.sam.springbatchcsvmysql;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.sam.springbatchcsvmysql.dao.IemployeeDao;
import com.sam.springbatchcsvmysql.entities.Customer;
import com.sam.springbatchcsvmysql.entities.Employee;
import com.sam.springbatchcsvmysql.repository.ICustomerRepository;
import com.sam.springbatchcsvmysql.repository.JpaQueryProviderImpl;
import com.sam.springbatchcsvmysql.step.jpa.DBReader;
import com.sam.springbatchcsvmysql.step.jpa.FileWriter;

/**
 * @author sumit
 *
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

	private String filePath = "C:\\Users\\sumit\\git\\spring-batch-csv-to-db\\data\\";

	private String inputFile = filePath + "customer-data.csv";

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public ICustomerRepository customerRepository;

	@Autowired
	public IemployeeDao employeeDao;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Bean
	public Job customerJob() {
		return jobBuilderFactory.get("customerJob").incrementer(new RunIdIncrementer())
				.listener(new com.sam.springbatchcsvmysql.step.jpa.Listener(customerRepository)).flow(customerJobStep())
				.end().build();
	}

	@Bean
	public Step customerJobStep() {
		return stepBuilderFactory.get("customerJobStep").<Customer, Customer>chunk(2)
				.reader(com.sam.springbatchcsvmysql.step.jpa.Reader.reader(inputFile))
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
				.reader(com.sam.springbatchcsvmysql.step.jdbc.Reader.reader(inputFile))
				.processor(new com.sam.springbatchcsvmysql.step.jdbc.Processor())
				.writer(new com.sam.springbatchcsvmysql.step.jdbc.Writer(employeeDao)).build();
	}

	@Bean
	public Job customerJobReverse()
			throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {
		return jobBuilderFactory.get("customerJobReverse").incrementer(new RunIdIncrementer())
				.listener(new com.sam.springbatchcsvmysql.step.jpa.Listener(customerRepository))
				.flow(customerJobReverseStep()).end().build();
	}

	@Bean
	public Step customerJobReverseStep()
			throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {

		return stepBuilderFactory.get("customerJobReverseStep").<Customer, Customer>chunk(2)
				.reader(reader())
				.processor(new com.sam.springbatchcsvmysql.step.jpa.Processor()).writer(writer()).build();
	}

	@Bean
	public JpaPagingItemReader<Customer> reader() throws Exception {
		JpaPagingItemReader<Customer> databaseReader = new JpaPagingItemReader<>();
		databaseReader.setEntityManagerFactory(entityManagerFactory);
		JpaQueryProviderImpl<Customer> jpaQueryProvider = new JpaQueryProviderImpl<>();
		jpaQueryProvider.setQuery("Customer.findAll");
		databaseReader.setQueryProvider(jpaQueryProvider);
		databaseReader.setPageSize(1000);
		databaseReader.afterPropertiesSet();
		return databaseReader;
	}
	
	@Bean
	  public StaxEventItemWriter<Customer> writer() {
	    StaxEventItemWriter<Customer> writer = new StaxEventItemWriter<>();
	    writer.setResource(new FileSystemResource(filePath+"\\customer.xml"));
	    writer.setMarshaller(customerUnmarshaller());
	    writer.setRootTagName("customers");
	    return writer;
	  }
	 
	  @Bean
	  public XStreamMarshaller customerUnmarshaller() {
	    XStreamMarshaller unMarshaller = new XStreamMarshaller();
	    @SuppressWarnings("rawtypes")
		Map<String, Class> aliases = new HashMap<String, Class>();
	    aliases.put("customer", Customer.class);
	    try {
			unMarshaller.setAliases(aliases);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return unMarshaller;
	  }

}
