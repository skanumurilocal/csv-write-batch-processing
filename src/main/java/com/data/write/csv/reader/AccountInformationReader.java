package com.data.write.csv.reader;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

import com.data.write.csv.model.AccountInformation;
import com.data.write.csv.rowmapper.AccountInformationRowMapper;

public class AccountInformationReader implements ItemReader<AccountInformation>{
	
	@Autowired
	@Qualifier("oracledatasource")
	DataSource oracleSource;
	
	@Autowired
	public JdbcTemplate jdbctemplate;
	
	@Value("${file.path}")
	private String filePath;
	
	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(oracleSource);
	}
	
	private String sqlStr = "SELECT * FROM customerdb.accountinfo acc inner join customerdb.customerinfo cust on \r\n" + 
			"acc.account_number = cust.account_number;";
	

	@Override
	public AccountInformation read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		List<AccountInformation> result= jdbctemplate.query(sqlStr, new AccountInformationRowMapper());
		return result.iterator().next();
	}

}
