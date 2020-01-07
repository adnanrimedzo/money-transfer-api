package com.example.service;

import com.example.exception.BankAccountNotFoundException;
import com.example.model.BankAccountDto;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class BankAccountServiceTest {

    @Inject
    BankAccountService bankAccountService;

    @Test
    void should_create() {
        //given
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setAmount(BigDecimal.valueOf(42L));

        //when
        BankAccountDto createdBankAccount = bankAccountService.create(bankAccountDto);

        //then
        assertEquals(0, BigDecimal.valueOf(42L).compareTo(createdBankAccount.getAmount()));
    }

    @Test
    void should_getAccount() {
        //given
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setAmount(BigDecimal.valueOf(42L));
        BankAccountDto createdBankAccount = bankAccountService.create(bankAccountDto);

        //when
        BankAccountDto account = bankAccountService.getAccount(createdBankAccount.getId());

        //then
        assertEquals(createdBankAccount.getId(), account.getId());
        assertEquals(0, BigDecimal.valueOf(42L).compareTo(account.getAmount()));
    }

    @Test
    void should_throw_exception_when_account_not_exist() {
        //then
        assertThrows(BankAccountNotFoundException.class, () -> bankAccountService.getAccount(-1L));
    }
}