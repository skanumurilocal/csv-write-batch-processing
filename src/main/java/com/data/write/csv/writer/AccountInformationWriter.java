package com.data.write.csv.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.data.write.csv.model.AccountInformation;

public class AccountInformationWriter implements ItemWriter<AccountInformation>{

	@Override
	public void write(List<? extends AccountInformation> items) throws Exception {
		items.stream().forEach(System.out::println);
		
	}

}
