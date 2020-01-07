package com.example.handler;

import javax.json.Json;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

public class ExceptionHandler {
    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Override
        public Response toResponse(Exception exception) {
            int code = 200;
            return Response.status(code)
                    .entity(Json.createObjectBuilder().add("error", exception.getMessage()).build())
                    .build();
        }
    }
}
