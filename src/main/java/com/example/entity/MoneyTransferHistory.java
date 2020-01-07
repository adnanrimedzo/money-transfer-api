package com.example.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Optional;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransferHistory extends PanacheEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender")
    private BankAccount sender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver")
    private BankAccount receiver;
    private BigDecimal amount;

    public static Optional<MoneyTransferHistory> findBankAccountHistoryRecord(Long id) {
        return find("id", id).firstResultOptional();
    }
}
