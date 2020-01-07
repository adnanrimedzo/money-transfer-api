package com.example.service;

import com.example.entity.MoneyTransferHistory;
import com.example.exception.BankAccountAmountNotSufficientException;
import com.example.exception.MoneyTransferRequestAlreadyExecutedException;
import com.example.model.BankAccountDto;
import com.example.model.MoneyTransferDto;
import com.example.model.MoneyTransferHistoryDto;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class MoneyTransferServiceTest {

    @Inject
    MoneyTransferService moneyTransferService;
    @Inject
    BankAccountService bankAccountService;

    @Test
    void should_transfer() {
        //given
        BankAccountDto bankAccountDto1 = new BankAccountDto();
        bankAccountDto1.setAmount(BigDecimal.valueOf(42L));
        BankAccountDto bankAccountDto2 = new BankAccountDto();
        bankAccountDto2.setAmount(BigDecimal.valueOf(84L));
        BankAccountDto createdBankAccount1 = bankAccountService.create(bankAccountDto1);
        BankAccountDto createdBankAccount2 = bankAccountService.create(bankAccountDto2);

        MoneyTransferDto moneyTransferDto = new MoneyTransferDto();
        moneyTransferDto.setAmount(BigDecimal.TEN);
        moneyTransferDto.setSenderAccountId(createdBankAccount1.getId());
        moneyTransferDto.setReceiverAccountId(createdBankAccount2.getId());

        //when
        MoneyTransferHistoryDto transfer = moneyTransferService.transfer(moneyTransferDto);

        //then
        assertEquals(0, BigDecimal.valueOf(32L).compareTo(transfer.getSender().getAmount()));
        assertEquals(0, BigDecimal.valueOf(94L).compareTo(transfer.getReceiver().getAmount()));
    }

    @Test
    void should_throw_exception_when_sender_has_not_sufficient_amount() {
        //given
        BankAccountDto bankAccountDto1 = new BankAccountDto();
        bankAccountDto1.setAmount(BigDecimal.valueOf(2L));
        BankAccountDto bankAccountDto2 = new BankAccountDto();
        bankAccountDto2.setAmount(BigDecimal.valueOf(84L));
        BankAccountDto createdBankAccount1 = bankAccountService.create(bankAccountDto1);
        BankAccountDto createdBankAccount2 = bankAccountService.create(bankAccountDto2);

        MoneyTransferDto moneyTransferDto = new MoneyTransferDto();
        moneyTransferDto.setAmount(BigDecimal.TEN);
        moneyTransferDto.setSenderAccountId(createdBankAccount1.getId());
        moneyTransferDto.setReceiverAccountId(createdBankAccount2.getId());

        //then
        assertThrows(BankAccountAmountNotSufficientException.class, () -> moneyTransferService.transfer(moneyTransferDto));
    }

    @Test
    void should_throw_exception_when_transaction_already_executed() {
        //given
        BankAccountDto bankAccountDto1 = new BankAccountDto();
        bankAccountDto1.setAmount(BigDecimal.valueOf(42L));
        BankAccountDto bankAccountDto2 = new BankAccountDto();
        bankAccountDto2.setAmount(BigDecimal.valueOf(84L));
        BankAccountDto createdBankAccount1 = bankAccountService.create(bankAccountDto1);
        BankAccountDto createdBankAccount2 = bankAccountService.create(bankAccountDto2);

        MoneyTransferDto moneyTransferDto = new MoneyTransferDto();
        moneyTransferDto.setAmount(BigDecimal.TEN);
        moneyTransferDto.setSenderAccountId(createdBankAccount1.getId());
        moneyTransferDto.setReceiverAccountId(createdBankAccount2.getId());

        moneyTransferService.transfer(moneyTransferDto);
        MoneyTransferHistory moneyTransferHistory = MoneyTransferHistory.findAll().firstResult();
        moneyTransferDto.setTransactionId(moneyTransferHistory.id);

        //then
        assertThrows(MoneyTransferRequestAlreadyExecutedException.class, () -> moneyTransferService.transfer(moneyTransferDto));
    }
}