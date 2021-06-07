package com.tenx.sample.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;

import org.springframework.jdbc.core.RowMapper;

import com.tenx.sample.model.Transaction;

public class TransactionRowMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Transaction transaction = new Transaction(rs.getLong("ID"), rs.getLong("SOURCE_ACCOUNT_ID"), rs.getLong("TARGET_ACCOUNT_ID"),
    			rs.getDouble("AMOUNT"), Currency.getInstance(rs.getString("CURRENCY")));

        return transaction;
    }
}