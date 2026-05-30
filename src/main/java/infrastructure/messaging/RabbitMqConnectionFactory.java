package infrastructure.messaging;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.InputStream;
import java.util.Properties;

public class RabbitMqConnectionFactory {
    private RabbitMqConnectionFactory() { }

    private static final ConnectionFactory FACTORY = new ConnectionFactory();

    static {
        try (InputStream input = RabbitMqConnectionFactory.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException("application.properties not found");
            }

            Properties props = new Properties();
            props.load(input);

            FACTORY.setHost(props.getProperty("rabbitmq.host"));
            FACTORY.setPort(Integer.parseInt(props.getProperty("rabbitmq.port")));
            FACTORY.setUsername(props.getProperty("rabbitmq.user"));
            FACTORY.setPassword(props.getProperty("rabbitmq.password"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection newConnection() throws Exception {
        return FACTORY.newConnection();
    }
}
