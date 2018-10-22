package com.data.write.csv.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.data.write.csv.model.AccountInformation;

public class AccountInformationRowMapper implements RowMapper<AccountInformation>{

	@Override
	public AccountInformation mapRow(ResultSet rs, int rowNum) throws SQLException {
		return AccountInformation.builder()
				.accountNumber(rs.getLong("account_number"))
				.stateName(rs.getString("state"))
				.phoneNumber(rs.getString("phone_number"))
				.customerName(rs.getString("customer"))
				.location01(rs.getInt("location01"))
				.location02(rs.getInt("location02"))
				.location03(rs.getInt("location03"))
				.location04(rs.getInt("location04"))
				.location05(rs.getInt("location05"))
				.location06(rs.getInt("location06"))
				.location07(rs.getInt("location07"))
				.location08(rs.getInt("location08"))
				.location09(rs.getInt("location09"))
				.location10(rs.getInt("location10"))
				.location11(rs.getInt("location11"))
				.location12(rs.getInt("location12"))
				.location13(rs.getInt("location13"))
				.location14(rs.getInt("location14"))
				.location15(rs.getInt("location15"))
				.phoneNumber(rs.getString("phone_number"))
				.phoneNumber01(rs.getString("phone_number01"))
				.phoneNumber02(rs.getString("phone_number02"))
				.phoneNumber03(rs.getString("phone_number03"))
				.phoneNumber04(rs.getString("phone_number04"))
				.phoneNumber05(rs.getString("phone_number05"))
				.phoneNumber06(rs.getString("phone_number06"))
				.phoneNumber07(rs.getString("phone_number07"))
				.phoneNumber08(rs.getString("phone_number08"))
				.phoneNumber09(rs.getString("phone_number09"))
				.phoneNumber10(rs.getString("phone_number10"))
				.phoneNumber11(rs.getString("phone_number11"))
				.phoneNumber12(rs.getString("phone_number12"))
				.phoneNumber13(rs.getString("phone_number13"))
				.phoneNumber14(rs.getString("phone_number14"))
				.phoneNumber15(rs.getString("phone_number15"))
				.build();
	}

}
