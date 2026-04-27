package api.controllers;

import application.dtos.StudyCreateDto;
import application.services.StudyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import domain.entities.Study;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

public class StudyController implements HttpHandler {

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final StudyService service = new StudyService();

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
                } else if (path.matches("/studies/[^/]+")) {
                    getById(exchange);
                } else {
                    getAll(exchange);
                }
            } else if ("PUT".equalsIgnoreCase(method)) {
                update(exchange);
            } else if ("DELETE".equalsIgnoreCase(method)) {
                delete(exchange);
            } else {
                send(exchange, "Method Not Allowed", 405);
            }
        } catch (Exception e) {
            send(exchange, "{\"error\":\"" + e.getMessage() + "\"}", 400);
        }
    }

    private void create(HttpExchange exchange) throws IOException {
        StudyCreateDto dto = mapper.readValue(exchange.getRequestBody(), StudyCreateDto.class);
        Study study = service.createStudy(dto);
        send(exchange, mapper.writeValueAsString(study), 201);
    }

    private void getAll(HttpExchange exchange) throws IOException {
        List<Study> studies = service.getAll();
        send(exchange, mapper.writeValueAsString(studies), 200);
    }

    private void getById(HttpExchange exchange) throws IOException {
        String idStr = exchange.getRequestURI().getPath().split("/")[2];
        Study study = service.getById(UUID.fromString(idStr));
        send(exchange, mapper.writeValueAsString(study), 200);
    }

    private void getByPrisoner(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        String idStr = query.split("=")[1];
        List<Study> studies = service.getByPrisonerId(UUID.fromString(idStr));
        send(exchange, mapper.writeValueAsString(studies), 200);
    }

    private void update(HttpExchange exchange) throws IOException {
        String idStr = exchange.getRequestURI().getPath().split("/")[2];
        StudyCreateDto dto = mapper.readValue(exchange.getRequestBody(), StudyCreateDto.class);

        Study updated = service.updateStudy(UUID.fromString(idStr), dto);

        send(exchange, mapper.writeValueAsString(updated), 200);
    }

    private void delete(HttpExchange exchange) throws IOException {
        String idStr = exchange.getRequestURI().getPath().split("/")[2];

        service.deleteStudy(UUID.fromString(idStr));

        send(exchange, "{\"message\":\"Deleted\"}", 200);
    }

    private void send(HttpExchange exchange, String response, int status) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, response.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
