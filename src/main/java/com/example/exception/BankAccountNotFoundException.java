package com.example.exception;

public class BankAccountNotFoundException extends RuntimeException {
    public BankAccountNotFoundException() {
        super("Bank account not found");
    }
}
