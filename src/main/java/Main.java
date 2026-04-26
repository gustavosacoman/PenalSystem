import com.sun.net.httpserver.HttpServer;

import api.controllers.*;
import infrastructure.ConnectionFactory;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args){

        try {
            ConnectionFactory.applyMigrations();

            HttpServer server = HttpServer.create(new InetSocketAddress(5000),0);
            
            server.createContext("/prisoners", new PrisonerController());
            server.createContext("/books", new BookController());
            server.createContext("/days-of-work", new DayOfWorkController());

            server.createContext("/ping", exchange -> {
                if ("GET".equals(exchange.getRequestMethod())) {
                    String response = "pong";

                    exchange.sendResponseHeaders(200, response.getBytes().length);

                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }else {
                    exchange.sendResponseHeaders(405, -1);
                }
            });

            server.setExecutor(Executors.newFixedThreadPool(10));

            server.start();
            System.out.printf("Server running...");
            System.out.printf("Running in port 5000");


        }catch (Exception e) {
            System.err.println("initializing failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
