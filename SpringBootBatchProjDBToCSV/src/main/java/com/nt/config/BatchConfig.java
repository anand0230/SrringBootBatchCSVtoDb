package com.nt.config;


import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.nt.listener.JobMonitoringListener;
import com.nt.model.Exam_Result;
import com.nt.processor.EmployeeInfoItemProcessor;

@Configuration
//@EnableBatchProcessing//this is not required it will take automatically by starter
public class BatchConfig {
	

	@Autowired
	private JobMonitoringListener listener;
	
	@Autowired
	private EmployeeInfoItemProcessor processor;
	
	//this only when we are using jdbc item writer 
	@Autowired
    private DataSource ds;	
	
	
	@Bean
	public JdbcCursorItemReader<Exam_Result> createReader() {
		
		          return new JdbcCursorItemReaderBuilder<Exam_Result>()
				.name("reader")
				.dataSource(ds)
				.sql("select id,dob,percentage,semester from exam_result")
				.beanRowMapper(Exam_Result.class)
				.build();	
		          
	}
	
	

	//this is for job repository
	//best version of writer method
public FlatFileItemWriter<Exam_Result> createWriter(){
	
    return new FlatFileItemWriterBuilder<Exam_Result>()
    		.name("writer")
   .resource(new FileSystemResource("topbrains.csv"))
   .lineSeparator("\r\n")
   .delimited().delimiter(",")
   .names("id","dob","percentage","semester")
   .build();
     
	     	}



@Bean("step1")
public Step createStep(JobRepository jobRepository,PlatformTransactionManager trmanager ) {
	return new StepBuilder("step1",jobRepository)
			.<Exam_Result,Exam_Result>chunk(2,trmanager)
			.reader(createReader())
			.processor(processor)
			.writer(createWriter())
			.build();		
}





@Bean("job1")
public Job createJob(JobRepository jobrepository,Step step) {
	
	return new JobBuilder("job1",jobrepository)
				.listener(listener)
			    .incrementer(new RunIdIncrementer())
			    .start(step)
			    .build();	
} 
	



}
