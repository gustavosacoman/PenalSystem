package infrastructure.messaging;

import application.events.ReleaseDateUpdatedEvent;
import application.messaging.EventPublisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class RabbitMqEventPublisher implements EventPublisher {

    public static final String EXCHANGE = "penal.events";
    public static final String ROUTING_KEY = "release.updated";

    private final Channel channel;
    private final ObjectMapper objectMapper;

    public RabbitMqEventPublisher() {
        try {
            Connection connection = RabbitMqConnectionFactory.newConnection();
            this.channel = connection.createChannel();

            this.channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT, true);

            this.objectMapper = new ObjectMapper();
            this.objectMapper.registerModule(new JavaTimeModule());
            this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        } catch (Exception e) {
            throw new RuntimeException("Failed to set up RabbitMQ publisher", e);
        }
    }

    @Override
    public void publish(ReleaseDateUpdatedEvent event) {
        try {
            byte[] body = objectMapper.writeValueAsBytes(event);

            channel.basicPublish(EXCHANGE, ROUTING_KEY, null, body);

        } catch (Exception e) {
            throw new RuntimeException("Failed to publish event", e);
        }
    }
}
