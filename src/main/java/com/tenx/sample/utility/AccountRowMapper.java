package com.tenx.sample.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;

import org.springframework.jdbc.core.RowMapper;

import com.tenx.sample.model.Account;

public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Account account = new Account(rs.getLong("ID"), rs.getDouble("BALANCE"), Currency.getInstance(rs.getString("CURRENCY")),
    			rs.getDate("CREATED_AT"));

        return account;
    }
}