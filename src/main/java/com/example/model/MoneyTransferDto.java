package com.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class MoneyTransferDto {
    @NonNull
    Long transactionId;
    @NonNull
    @Positive
    Long senderAccountId;
    @NonNull
    @Positive
    Long receiverAccountId;
    @NonNull
    @Positive
    BigDecimal amount;
}
