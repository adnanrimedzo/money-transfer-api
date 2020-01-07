package com.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MoneyTransferHistoryDto {
    private BankAccountDto sender;
    private BankAccountDto receiver;
}
