package com.example.service;

import com.example.entity.BankAccount;
import com.example.entity.MoneyTransferHistory;
import com.example.exception.BankAccountAmountNotSufficientException;
import com.example.exception.BankAccountNotFoundException;
import com.example.exception.MoneyTransferRequestAlreadyExecutedException;
import com.example.mapper.Converter;
import com.example.model.MoneyTransferDto;
import com.example.model.MoneyTransferHistoryDto;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.math.BigDecimal;

@Singleton
public class MoneyTransferService {
    private Converter converter;

    @Inject
    public MoneyTransferService(Converter converter) {
        this.converter = converter;
    }

    @Transactional
    public MoneyTransferHistoryDto transfer(MoneyTransferDto moneyTransferDto) {
        BankAccount sender = getBankAccount(moneyTransferDto.getSenderAccountId());
        BigDecimal amount = moneyTransferDto.getAmount();
        validateBankAccountAmountSufficiency(sender, amount);
        BankAccount receiver = getBankAccount(moneyTransferDto.getReceiverAccountId());

        sender.updateAmount(amount.negate());
        receiver.updateAmount(amount);

        validateTransactionNotExist(moneyTransferDto.getTransactionId());
        MoneyTransferHistory moneyTransferHistory = new MoneyTransferHistory(sender, receiver, amount);
        moneyTransferHistory.persist();

        return converter.map(moneyTransferHistory, MoneyTransferHistoryDto.class);
    }

    private BankAccount getBankAccount(Long senderAccountId) {
        return BankAccount.findByIdWithUpdateLock(senderAccountId)
                .orElseThrow(BankAccountNotFoundException::new);
    }

    private void validateTransactionNotExist(Long transactionId) {
        if (validateTransactionExist(transactionId)) {
            throw new MoneyTransferRequestAlreadyExecutedException();
        }
    }

    private boolean validateTransactionExist(Long transactionId) {
        return MoneyTransferHistory.findBankAccountHistoryRecord(transactionId).isPresent();
    }

    private void validateBankAccountAmountSufficiency(BankAccount bankAccount, BigDecimal amount) {
        if (bankAccount.getAmount().compareTo(amount) < 0) {
            throw new BankAccountAmountNotSufficientException();
        }
    }
}
