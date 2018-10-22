package com.data.write.csv.writer;

import java.util.List;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.io.FileSystemResource;

import com.data.write.csv.model.AccountInformation;

public class AccountInformationWriter extends FlatFileItemWriter<AccountInformation>{
	
	private String headerString = "accountNumber,stateName,customerName,phoneNumber,location01,location02,location03,"
			+ "location04,location05,location06,location07,location08,location09,location10,location11,location12,location13,"
			+ "location14,location15,phonenumber01,phonenumber02,phonenumber03,phonenumber04,phonenumber05"
			+ ",phonenumber06,phonenumber07,phonenumber08,phonenumber09,phonenumber10,phonenumber11,phonenumber12,phonenumber13"
			+ ",phonenumber14,phonenumber15";
	private String[] extractorNames =new String[] {"accountNumber","stateName","customerName","phoneNumber","location01","location02",
			"location03","location04","location05","location06","location07","location08","location09",
			"location10","location11","location12","location13",
			"location14","location15","phoneNumber01","phoneNumber02","phoneNumber03","phoneNumber04","phoneNumber05",
			"phoneNumber06","phoneNumber07","phoneNumber08","phoneNumber09","phoneNumber10","phoneNumber11",
			"phoneNumber12","phoneNumber13","phoneNumber14","phoneNumber15"};
	
	
	public AccountInformationWriter() {
		CsvHeaderWriter headerWriter = new CsvHeaderWriter(headerString);
        this.setHeaderCallback(headerWriter);
 
        String exportFilePath = "C:\\work\\accountsInfo.csv";
        this.setResource(new FileSystemResource(exportFilePath));
 
        LineAggregator<AccountInformation> lineAggregator = createAccountLineAggregator();
        this.setLineAggregator(lineAggregator);
	}
	
	private LineAggregator<AccountInformation> createAccountLineAggregator() {
        DelimitedLineAggregator<AccountInformation> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
 
        FieldExtractor<AccountInformation> fieldExtractor = createAccountFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);
 
        return lineAggregator;
    }
	
	 private FieldExtractor<AccountInformation> createAccountFieldExtractor() {
	        BeanWrapperFieldExtractor<AccountInformation> extractor = new BeanWrapperFieldExtractor<>();
	        extractor.setNames(extractorNames);
	        return extractor;
	    }

	

}
