package com.example.mapper;

import com.example.model.BankAccountDto;
import org.modelmapper.ModelMapper;

import javax.inject.Singleton;

@Singleton
public class Converter extends ModelMapper {
    public Converter() {
        this.addMappings(BankAccountDto.personMap);
    }
}
