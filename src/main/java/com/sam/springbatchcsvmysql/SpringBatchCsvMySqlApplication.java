package com.sam.springbatchcsvmysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sam.springbatchcsvmysql.step.jdbc.Listener;

/**
 * @author sumit
 *
 */
@SpringBootApplication
public class SpringBatchCsvMySqlApplication implements CommandLineRunner {
	
	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job customerJob;
	
	@Autowired
	Job employeeJob;
	
	private static final Logger log = LoggerFactory.getLogger(SpringBatchCsvMySqlApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchCsvMySqlApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		try {
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
					.toJobParameters();
			jobLauncher.run(customerJob, jobParameters);
			
			logger.info("Customer job complete...");
			
			JobParameters jobParameters1 = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
					.toJobParameters();
			jobLauncher.run(employeeJob, jobParameters1);
			
			logger.info("Employee job complete...");
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		
	}
}
