package cl.rutaexpress.rabbitadmin;

import java.util.Map;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitAdminController {
    private final RabbitAdmin rabbitAdmin;
    public RabbitAdminController(RabbitAdmin rabbitAdmin) { this.rabbitAdmin = rabbitAdmin; }

    @GetMapping("/api/rabbitmq/status")
    public Map<String, Object> status() {
        return Map.of("service", "rabbitmq-admin", "connected", rabbitAdmin.getRabbitTemplate().getConnectionFactory().createConnection().isOpen());
    }
}
