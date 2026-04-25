package api.controllers;

import application.dtos.BookCreateDto;
import application.services.BookService;
import domain.entities.Book;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

public class BookController implements HttpHandler {

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final BookService bookService = new BookService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();

        if (method.equalsIgnoreCase("POST")) {
            createBook(exchange);
        } else if (method.equalsIgnoreCase("GET")) {
            getBooks(exchange);
        } else {
            send(exchange, "{\"error\":\"Method not allowed\"}", 405);
        }
    }

    private void createBook(HttpExchange exchange) throws IOException {
        try {
            InputStream requestBody = exchange.getRequestBody();

            BookCreateDto dto = mapper.readValue(requestBody, BookCreateDto.class);

            Book book = bookService.createBook(dto);

            String response = mapper.writeValueAsString(book);

            send(exchange, response, 201);

        } catch (Exception e) {
            e.printStackTrace();
            send(exchange,
                    "{\"error\": \"Failed to create book: " + e.getMessage() + "\"}",
                    400);
        }
    }

    private void getBooks(HttpExchange exchange) throws IOException {
        try {
            String query = exchange.getRequestURI().getQuery();

            if (query == null || !query.contains("prisonerId=")) {
                send(exchange, "{\"error\":\"Missing prisonerId\"}", 400);
                return;
            }

            String prisonerIdStr = query.split("=")[1];
            UUID prisonerId = UUID.fromString(prisonerIdStr);

            List<Book> books = bookService.getByPrisonerId(prisonerId);

            String response = mapper.writeValueAsString(books);

            send(exchange, response, 200);

        } catch (Exception e) {
            e.printStackTrace();
            send(exchange,
                    "{\"error\": \"Failed to fetch books: " + e.getMessage() + "\"}",
                    400);
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