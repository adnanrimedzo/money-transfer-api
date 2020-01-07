package com.example;

import com.example.model.BankAccountDto;
import com.example.model.MoneyTransferDto;
import com.example.service.BankAccountService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class MoneyTransferControllerTest {
    @Inject
    BankAccountService bankAccountService;

    @Test
    public void test_moneyTransfer_endpoint() {
        {
            //given
            BankAccountDto bankAccountDto1 = new BankAccountDto();
            bankAccountDto1.setAmount(BigDecimal.valueOf(42L));
            BankAccountDto bankAccountDto2 = new BankAccountDto();
            bankAccountDto2.setAmount(BigDecimal.valueOf(84L));
            BankAccountDto createdBankAccount1 = bankAccountService.create(bankAccountDto1);
            BankAccountDto createdBankAccount2 = bankAccountService.create(bankAccountDto2);

            MoneyTransferDto moneyTransferDto = new MoneyTransferDto();
            moneyTransferDto.setAmount(BigDecimal.TEN);
            moneyTransferDto.setSenderAccountId(createdBankAccount1.getId());
            moneyTransferDto.setReceiverAccountId(createdBankAccount2.getId());

            given()
                    .when().contentType(MediaType.APPLICATION_JSON)
                    .body("{\n" +
                            "  \"amount\": 8,\n" +
                            "  \"receiverAccountId\": " + createdBankAccount1.getId() + ",\n" +
                            "  \"senderAccountId\": " + createdBankAccount2.getId() + ",\n" +
                            "  \"transactionId\": 0\n" +
                            "}")
                    .post("/money-transfer")
                    .then()
                    .statusCode(201)
                    .body(containsString("{\"receiver\":{\"amount\":50.00,\"id\":1},\"sender\":{\"amount\":76.00,\"id\":2}}"));
        }

    }
}