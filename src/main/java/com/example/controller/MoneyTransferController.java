package com.example.controller;

import com.example.model.MoneyTransferDto;
import com.example.service.MoneyTransferService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/money-transfer")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MoneyTransferController {
    private MoneyTransferService moneyTransferService;

    @Inject
    public MoneyTransferController(MoneyTransferService moneyTransferService) {
        this.moneyTransferService = moneyTransferService;
    }

    @POST
    public Response transferMoney(@Valid MoneyTransferDto moneyTransferDto) {
        return Response.ok(moneyTransferService.transfer(moneyTransferDto)).status(201).build();
    }
}