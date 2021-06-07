package com.tenx.sample.dto;

import org.springframework.core.convert.converter.Converter;

import com.tenx.sample.model.Account;

public class AccountDTOConverter implements Converter<Account, AccountDTO> {

    @Override
    public AccountDTO convert(final Account account) {
    	return new AccountDTO(account.getId(), account.getBalance(), account.getCurrency().getCurrencyCode(),account.getCreatedAt());
    }
}
