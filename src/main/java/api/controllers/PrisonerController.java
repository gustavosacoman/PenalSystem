package api.controllers;

import application.dtos.Prisoner.CreatePrisonerDto;
import application.dtos.Prisoner.UpdatePrisonerDto;
import application.services.PrisonerService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import domain.entities.Prisoner;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class PrisonerController implements HttpHandler {

    private ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private PrisonerService prisonerService = new PrisonerService();
    private record IdentifierResult(UUID id, String cpf) {}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("GET")){
            String path = exchange.getRequestURI().getPath();
            String[] segments = path.split("/");
            String last = segments.length == 0 ? "" : segments[segments.length - 1];
            if (last.equals("prisoners") || last.isBlank()) {
                getAllPrisoners(exchange);
            } else {
                getPrisonerByIdOrCpf(exchange);
            }
        } else if(exchange.getRequestMethod().equals("POST"))
            createPrisoner(exchange);
        else if (exchange.getRequestMethod().equals("PUT"))
            updatePrisoner(exchange);
        else if (exchange.getRequestMethod().equals("DELETE"))
            deletePrisonerByCpfOrId(exchange);
    }

    private IdentifierResult treatIdOrCpf(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();

        String[] pathSegments = path.split("/");
        String identifier = pathSegments[pathSegments.length - 1];

        if (identifier.equals("prisoners") || identifier.isBlank()) {
            return null;
        }

        UUID id = null;
        String cpf = null;

        if (identifier.length() == 36)
            return new IdentifierResult(UUID.fromString(identifier), null);
        else
            return new IdentifierResult(null, identifier);
    }


    private void getAllPrisoners(HttpExchange exchange) throws IOException {
        try {
            var prisoners = prisonerService.getAll();
            String response = mapper.writeValueAsString(prisoners);
            send(exchange, response, 200);
        } catch (Exception e) {
            send(exchange, "{\"error\": \"" + e.getMessage() + "\"}", 500);
        }
    }

    private void getPrisonerByIdOrCpf(HttpExchange exchange) throws  IOException {

        try {
            var idOrCpf = treatIdOrCpf(exchange);

            if (idOrCpf == null) {
                send(exchange, "{\"error\": \"id or cpf in url cannot be null\"}", 400);
                return;
            }

            Prisoner p = null;

            if (idOrCpf.id != null)
                p = prisonerService.getPrisonerById(idOrCpf.id);
            else
                p = prisonerService.getPrisonerByCpf(idOrCpf.cpf);

            String response = mapper.writeValueAsString(p);
            send(exchange, response, 200);
        }
        catch (Exception e) {
            send(exchange, "Error: " + e.getMessage(), 500);
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

    private void updatePrisoner(HttpExchange exchange) throws  IOException{

        try {

            var idOrCpf = treatIdOrCpf(exchange);

            if (idOrCpf == null) {
                send(exchange, "{\"error\": \"id or cpf were not informed.\"}", 400);
                return;
            }

            InputStream requestBody = exchange.getRequestBody();
            UpdatePrisonerDto p = mapper.readValue(requestBody, UpdatePrisonerDto.class);

            prisonerService.updatePrisoner(p, idOrCpf.id, idOrCpf.cpf);

            send(exchange, "{\"message\": \"Prisoner are updated successful!\"}", 200);
        } catch (IllegalArgumentException e ) {
            e.printStackTrace();
            send(exchange, "{\"error\": \"" + e.getMessage() + "\"}", 400);
        } catch (Exception e) {
            e.printStackTrace();
            send(exchange, "{\"error\": \"Internal server error.\"}", 500);
        }
    }
    public void deletePrisonerByCpfOrId(HttpExchange exchange) throws IOException {

        try {
            var idOrCpf = treatIdOrCpf(exchange);

            prisonerService.DeletePrisonerByCpfOrId(idOrCpf.cpf, idOrCpf.id);

            send(exchange, "Prisoner deleted successful", 200);
        }
        catch (Exception e) {
            send(exchange, "Error : " + e.getMessage(), 500);
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
