package com.example.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BankAccount extends PanacheEntity {
    private BigDecimal amount;

    public static Optional<BankAccount> findById(Long id) {
        return find("id", id).firstResultOptional();
    }

    public static Optional<BankAccount> findByIdWithUpdateLock(Long id) {
        return find("id", id).withLock(LockModeType.PESSIMISTIC_WRITE).firstResultOptional();
    }

    public void updateAmount(BigDecimal amount) {
        this.amount = this.amount.add(amount);
        this.persist();
    }
}
