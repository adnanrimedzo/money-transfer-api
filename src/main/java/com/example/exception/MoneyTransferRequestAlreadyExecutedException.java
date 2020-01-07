package com.example.exception;

public class MoneyTransferRequestAlreadyExecutedException extends RuntimeException {
    public MoneyTransferRequestAlreadyExecutedException() {
        super("Money transfer request already executed");
    }
}
