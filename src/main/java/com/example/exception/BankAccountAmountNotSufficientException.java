package com.example.exception;

public class BankAccountAmountNotSufficientException extends RuntimeException {
    public BankAccountAmountNotSufficientException() {
        super("Bank account has no sufficient amount");
    }
}
