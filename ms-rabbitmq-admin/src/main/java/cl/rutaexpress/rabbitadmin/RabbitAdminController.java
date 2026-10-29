package cl.rutaexpress.rabbitadmin;

import java.util.Map;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitAdminController {
    private final ConnectionFactory connectionFactory;

    public RabbitAdminController(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @GetMapping("/api/rabbitmq/status")
    public Map<String, Object> status() {
        boolean connected = false;

        try (var connection = connectionFactory.createConnection()) {
            connected = connection.isOpen();
        } catch (Exception ignored) {
            connected = false;
        }

        return Map.of("service", "rabbitmq-admin", "connected", connected);
    }
}
