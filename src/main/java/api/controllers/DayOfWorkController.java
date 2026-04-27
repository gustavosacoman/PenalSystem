package api.controllers;

import application.dtos.DayOfWorkCreateDto;
import application.services.DayOfWorkService;
import domain.entities.DayOfWork;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class DayOfWorkController implements HttpHandler {

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final DayOfWorkService service = new DayOfWorkService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        try {
            if ("POST".equalsIgnoreCase(method)) {
                create(exchange);
            } else if ("GET".equalsIgnoreCase(method)) {
                if (exchange.getRequestURI().getQuery() != null) {
                    getByPrisoner(exchange);
                } else if (path.matches("/days-of-work/[^/]+")) {
                    getById(exchange);
                } else {
                    getAll(exchange);
                }
            } else if ("DELETE".equalsIgnoreCase(method)) {
                delete(exchange);
            } else {
                send(exchange, "Method Not Allowed", 405);
            }
        } catch (NoSuchElementException e) {
            send(exchange, "{\"error\":\"" + e.getMessage() + "\"}", 404);
        } catch (Exception e) {
            send(exchange, "{\"error\":\"" + e.getMessage() + "\"}", 400);
        }
    }

    private void create(HttpExchange exchange) throws IOException {
        DayOfWorkCreateDto dto = mapper.readValue(exchange.getRequestBody(), DayOfWorkCreateDto.class);
        DayOfWork dayOfWork = service.createDayOfWork(dto);
        send(exchange, mapper.writeValueAsString(toResponseDto(dayOfWork)), 201);
    }

    private void getAll(HttpExchange exchange) throws IOException {
        List<DayOfWork> daysOfWork = service.getAll();
        send(exchange, mapper.writeValueAsString(toResponseList(daysOfWork)), 200);
    }

    private void getById(HttpExchange exchange) throws IOException {
        String idStr = exchange.getRequestURI().getPath().split("/")[2];
        DayOfWork dayOfWork = service.getById(UUID.fromString(idStr));
        send(exchange, mapper.writeValueAsString(toResponseDto(dayOfWork)), 200);
    }

    private void getByPrisoner(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query == null || !query.startsWith("cpf=")) {
            throw new IllegalArgumentException("Use query parameter cpf");
        }

        String cpf = query.substring("cpf=".length());
        List<DayOfWork> daysOfWork = service.getByPrisonerCpf(cpf);
        send(exchange, mapper.writeValueAsString(toResponseList(daysOfWork)), 200);
    }

    private void delete(HttpExchange exchange) throws IOException {
        String idStr = exchange.getRequestURI().getPath().split("/")[2];

        service.deleteDayOfWork(UUID.fromString(idStr));

        send(exchange, "{\"message\":\"Deleted\"}", 200);
    }

    private void send(HttpExchange exchange, String response, int status) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, response.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private DayOfWorkResponseDto toResponseDto(DayOfWork dayOfWork) {
        String cpf = service.getPrisonerCpfById(dayOfWork.getPrisonerId());

        return new DayOfWorkResponseDto(
                dayOfWork.getId(),
                cpf,
                dayOfWork.getDate(),
                dayOfWork.getDescription());
    }

    private List<DayOfWorkResponseDto> toResponseList(List<DayOfWork> daysOfWork) {
        List<DayOfWorkResponseDto> response = new ArrayList<>();

        for (DayOfWork dayOfWork : daysOfWork) {
            response.add(toResponseDto(dayOfWork));
        }

        return response;
    }

    private record DayOfWorkResponseDto(UUID id, String cpf, LocalDate date, String description) {
    }
}
