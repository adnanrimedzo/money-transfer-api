package com.example;

import com.example.model.BankAccountDto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class BankAccountControllerTest {

    @Test
    public void test_bankAccount_endpoints() {
        ExtractableResponse<Response> response = given()
                .when().contentType(MediaType.APPLICATION_JSON)
                .body("{\"amount\":70}")
                .post("/bankAccounts")
                .then()
                .statusCode(201)
                .body(containsString("\"amount\":70")).extract();

        given()
                .when().contentType(MediaType.APPLICATION_JSON)
                .get("/bankAccounts")
                .then()
                .statusCode(200)
                .body(containsString("\"amount\":70"));

        BankAccountDto bankAccountDto = response.body().as(BankAccountDto.class);

        given()
                .when().contentType(MediaType.APPLICATION_JSON)
                .pathParam("id", bankAccountDto.getId())
                .get("/bankAccounts/{id}")
                .then()
                .statusCode(200)
                .body(containsString("\"amount\":70"))
                .body(containsString("\"id\":" + bankAccountDto.getId()));
    }

}