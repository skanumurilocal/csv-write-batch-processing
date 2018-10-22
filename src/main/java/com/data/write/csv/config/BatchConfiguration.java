package com.data.write.csv.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import com.data.write.csv.model.AccountInformation;
import com.data.write.csv.processor.AccountInformationProcessor;
import com.data.write.csv.rowmapper.AccountInformationRowMapper;
import com.data.write.csv.writer.AccountInformationWriter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	@Autowired
	 public JobBuilderFactory jobBuilderFactory;
	 
	 @Autowired
	 public StepBuilderFactory stepBuilderFactory;
	 
	 private String sqlStr = "SELECT * FROM customerdb.accountinfo acc inner join customerdb.customerinfo cust on \r\n" + 
				"acc.account_number = cust.account_number;";
	 
	@Bean(name="h2datasource")
	@Primary
	public DataSource dataSource(){
	   DriverManagerDataSource datasource = new DriverManagerDataSource();
	   datasource.setDriverClassName("org.h2.Driver");
	   datasource.setUrl("jdbc:h2:mem:AZ;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=TRUE");
	   datasource.setUsername("sa");
	   datasource.setPassword("");
	   return datasource;
	}
	 
	 @Bean(name="oracledatasource")
	 public DataSource oracleSource() {
		 DriverManagerDataSource oracleSource = new DriverManagerDataSource();
		 oracleSource.setDriverClassName("com.mysql.jdbc.Driver");
		 oracleSource.setUrl("jdbc:mysql://localhost:3306/customerdb");
		 oracleSource.setUsername("root");
		 oracleSource.setPassword("password123");
		return oracleSource;
	 }
	 
	 @Bean
	 public JdbcCursorItemReader<AccountInformation> reader() throws JsonParseException, IOException{
		
		  String sqlWithwhereClause = sqlStr+readJsonData();
		  System.out.println(sqlWithwhereClause);
		  JdbcCursorItemReader<AccountInformation> reader = new JdbcCursorItemReader<AccountInformation>();
		  reader.setDataSource(oracleSource());
		  reader.setSql(sqlStr);
		  reader.setRowMapper(new AccountInformationRowMapper());
		  
		  return reader;
	}
	 
	 String readJsonData() throws IOException{
		 byte[] jsonData = Files.readAllBytes(Paths.get("C:\\work\\generatedjson.json"));

		//create ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper();

		//read JSON like DOM Parser
		JsonNode rootNode = objectMapper.readTree(jsonData);
		JsonNode idNode = rootNode.path("query");
		System.out.println("id = "+idNode.asText());
		return idNode.asText();
	 }
	 
	 @Bean
	 public FlatFileItemWriter<AccountInformation> writer(){
	  FlatFileItemWriter<AccountInformation> writer = new FlatFileItemWriter<AccountInformation>();
	  writer.setResource(new ClassPathResource("users.csv"));
	  writer.setLineAggregator(new DelimitedLineAggregator<AccountInformation>() {{
	   setDelimiter(",");
	   setFieldExtractor(new BeanWrapperFieldExtractor<AccountInformation>() {{
	    setNames(new String[] { "accountNumber", "location01" });
	   }});
	  }});
	  
	  return writer;
	 }
	 
	 @Bean
    public ItemWriter<AccountInformation> databaseCsvItemWriter() {
        FlatFileItemWriter<AccountInformation> csvFileWriter = new AccountInformationWriter();
 
        return csvFileWriter;
    }
	 
	@Bean
	public AccountInformationProcessor processor(){
	  return new AccountInformationProcessor();
	}
	 
	@Bean
	 public Step step1() throws JsonParseException, IOException {
		 return stepBuilderFactory.get("step1")
				 .<AccountInformation,AccountInformation>chunk(10)
				 .reader(reader())
				 .processor(processor())
				 .writer(databaseCsvItemWriter())
				 .build();
	 }
	 
	 @Bean
		public PlatformTransactionManager getTransactionManager() {
			return new ResourcelessTransactionManager();
		}
	 
	 @Bean
		public JobRepository getJobRepo() throws Exception {
			JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
			factory.setDataSource(dataSource());
			factory.setTransactionManager(getTransactionManager());
			factory.setIsolationLevelForCreate("PROPAGATION_REQUIRES_NEW");
			return factory.getObject();
		}

		@Bean
		public Job copyFlatFileJob() throws JsonParseException, IOException {
			return jobBuilderFactory.get("csvfilecreatejob").incrementer(new RunIdIncrementer()).start(step1()).build();
		}
	 

}
