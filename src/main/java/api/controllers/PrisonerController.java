package api.controllers;

import application.dtos.CreatePrisonerDto;
import application.services.PrisonerService;
import domain.entities.Prisoner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PrisonerController implements HttpHandler {

    private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private PrisonerService prisonerService = new PrisonerService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("GET")){

        } else if(exchange.getRequestMethod().equals("POST")){
            createPrisoner(exchange);
        }
    }

    private void createPrisoner(HttpExchange exchange) throws IOException {
        try {
            InputStream requestBody = exchange.getRequestBody();
            CreatePrisonerDto p = mapper.readValue(requestBody, CreatePrisonerDto.class);
            prisonerService.addPrisoner(p);

            String response = "{\"message\": \"Registered Successful!\"}";
            send(exchange, response, 201);

        } catch (Exception e) {
            e.printStackTrace();
            String errorResponse = "{\"error\": \"Failed to register: " + e.getMessage() + "\"}";
            send(exchange, errorResponse, 400);
        }
    }
    private void send(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
