package cl.rutaexpress.rabbitadmin;

import java.util.Map;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitAdminController {
    private final RabbitTemplate rabbitTemplate;
    public RabbitAdminController(RabbitTemplate rabbitTemplate) { this.rabbitTemplate = rabbitTemplate; }

    @GetMapping("/api/rabbitmq/status")
    public Map<String, Object> status() {
        return Map.of("service", "rabbitmq-admin", "connected", rabbitTemplate.getConnectionFactory().createConnection().isOpen());
    }
}
