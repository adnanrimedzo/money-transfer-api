package com.example.model;

import com.example.entity.BankAccount;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.modelmapper.PropertyMap;

import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class BankAccountDto {
    @NonNull
    @PositiveOrZero
    private BigDecimal amount;
    private Long id;

    @JsonIgnore
    public static PropertyMap<BankAccount, BankAccountDto> personMap = new PropertyMap<BankAccount, BankAccountDto>() {
        protected void configure() {
            map().setId(source.id);
        }
    };
}
