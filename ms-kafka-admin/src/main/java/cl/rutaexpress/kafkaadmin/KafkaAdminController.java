package cl.rutaexpress.kafkaadmin;

import java.util.Map;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaAdminController {
    private final AdminClient adminClient;
    public KafkaAdminController(AdminClient adminClient) { this.adminClient = adminClient; }

    @GetMapping("/api/kafka/status")
    public Map<String, Object> status() throws Exception {
        return Map.of("service", "kafka-admin", "topics", adminClient.listTopics().names().get());
    }
}
