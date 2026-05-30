package infrastructure.messaging;

import application.events.ReleaseDateUpdatedEvent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

public class ReleaseNotificationConsumer {

    private static final String QUEUE = "release.notifications";

    private final ObjectMapper objectMapper;

    public ReleaseNotificationConsumer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public void start() throws Exception {
        Connection connection = RabbitMqConnectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(RabbitMqEventPublisher.EXCHANGE, BuiltinExchangeType.DIRECT, true);

        channel.queueDeclare(QUEUE, true, false, false, null);

        channel.queueBind(QUEUE, RabbitMqEventPublisher.EXCHANGE, RabbitMqEventPublisher.ROUTING_KEY);

        DeliverCallback onMessage = (consumerTag, delivery) -> {
            ReleaseDateUpdatedEvent event = objectMapper.readValue(delivery.getBody(), ReleaseDateUpdatedEvent.class);

            System.out.printf(
                    "Notificando: pena do prisoner %s reduzida em %d dia(s) por %s. Nova data de soltura: %s%n",
                    event.prisonerId(), event.daysReduced(), event.activityType(), event.newReleaseDate());
        };

        channel.basicConsume(QUEUE, true, onMessage, consumerTag -> {});

        System.out.println("ReleaseNotificationConsumer listening on queue '" + QUEUE + "'");
    }
}
