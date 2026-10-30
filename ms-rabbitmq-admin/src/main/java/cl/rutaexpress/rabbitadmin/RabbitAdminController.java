package cl.rutaexpress.rabbitadmin;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RabbitAdminController {
    private final ConnectionFactory connectionFactory;
    private final RestTemplate restTemplate;
    private final String managementUrl;

    public RabbitAdminController(ConnectionFactory connectionFactory,
                                 @Value("${spring.rabbitmq.management.url:${rabbitmq.management.url:http://rabbitmq:15672}}") String managementUrl,
                                 @Value("${spring.rabbitmq.username:rutaexpress}") String username,
                                 @Value("${spring.rabbitmq.password:rutaexpress}") String password) {
        this.connectionFactory = connectionFactory;
        this.managementUrl = managementUrl.replaceAll("/$", "");
        this.restTemplate = new RestTemplate();
        String credentials = username + ':' + password;
        String token = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        this.restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Basic " + token);
            return execution.execute(request, body);
        });
    }

    @GetMapping("/api/rabbitmq/status")
    public Map<String, Object> status() {
        boolean connected = false;
        try (var connection = connectionFactory.createConnection()) {
            connected = connection.isOpen();
        } catch (Exception ignored) {
            connected = false;
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("service", "rabbitmq-admin");
        result.put("connected", connected);
        result.put("exchanges", List.of("cmd.topic", "cmd.direct", "cmd.dead.dlx"));
        return result;
    }

    @GetMapping("/api/rabbitmq/queues")
    public List<Map<String, Object>> queues() {
        Set<String> rutaExpressQueues = Set.of(
                "shipment.created.queue", "shipment.created.dlq",
                "shipment.status.changed.queue", "shipment.status.changed.dlq",
                "shipment.detail.queue", "shipment.detail.dlq");

        try {
            List<Map<String, Object>> queues = restTemplate.exchange(
                    managementUrl + "/api/queues",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}).getBody();

            if (queues == null) return List.of();
            return queues.stream()
                    .filter(queue -> rutaExpressQueues.contains(String.valueOf(queue.get("name"))))
                    .map(this::queueSummary)
                    .toList();
        } catch (Exception exception) {
            return List.of();
        }
    }

    private Map<String, Object> queueSummary(Map<String, Object> queue) {
        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("name", queue.get("name"));
        summary.put("messages", queue.getOrDefault("messages", 0));
        summary.put("ready", queue.getOrDefault("messages_ready", 0));
        summary.put("unacknowledged", queue.getOrDefault("messages_unacknowledged", 0));
        summary.put("consumers", queue.getOrDefault("consumers", 0));
        summary.put("state", queue.getOrDefault("state", "unknown"));
        return summary;
    }
}
