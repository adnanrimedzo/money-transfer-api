package com.example.service;

import com.example.entity.BankAccount;
import com.example.exception.BankAccountNotFoundException;
import com.example.mapper.Converter;
import com.example.model.BankAccountDto;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
public class BankAccountService {
    private Converter converter;

    @Inject
    public BankAccountService(Converter converter) {
        this.converter = converter;
    }

    @Transactional
    public BankAccountDto create(BankAccountDto bankAccountDto) {
        BankAccount bankAccount = converter.map(bankAccountDto, BankAccount.class);
        bankAccount.persistAndFlush();
        return converter.map(bankAccount, BankAccountDto.class);
    }

    public BankAccountDto getAccount(Long id) {
        return BankAccount.findById(id)
                .map(bankAccount -> converter.map(bankAccount, BankAccountDto.class))
                .orElseThrow(BankAccountNotFoundException::new);
    }

    public List<BankAccountDto> getAccounts(){
        return BankAccount.findAll().stream()
                .map(bankAccountEntity -> converter.map(bankAccountEntity, BankAccountDto.class))
                .collect(Collectors.toList());
    }
}
