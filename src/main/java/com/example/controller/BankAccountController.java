package com.example.controller;

import com.example.model.BankAccountDto;
import com.example.service.BankAccountService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/bankAccounts")
public class BankAccountController {
    private BankAccountService bankAccountService;

    @Inject
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GET
    @Path("/{id}")
    public Response getBankAccount(@PathParam("id") long id) {
        return Response.ok(bankAccountService.getAccount(id)).status(200).build();
    }

    @GET
    public Response getBankAccounts() {
        return Response.ok(bankAccountService.getAccounts()).status(200).build();
    }

    @POST
    public Response createBankAccount(@Valid BankAccountDto bankAccountDto) {
        return Response.ok(bankAccountService.create(bankAccountDto)).status(201).build();
    }
}