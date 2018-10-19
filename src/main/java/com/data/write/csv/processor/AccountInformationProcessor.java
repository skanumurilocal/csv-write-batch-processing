package com.data.write.csv.processor;

import org.springframework.batch.item.ItemProcessor;

import com.data.write.csv.model.AccountInformation;

public class AccountInformationProcessor implements ItemProcessor<AccountInformation, AccountInformation> {

	 @Override
	 public AccountInformation process(AccountInformation accountInfo) throws Exception {
	  return accountInfo;
	 }

}
