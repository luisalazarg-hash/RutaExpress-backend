package cl.rutaexpress.audit;

import cl.rutaexpress.audit.service.AuditService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AuditListener {
    private final AuditService auditService;

    public AuditListener(AuditService auditService) {
        this.auditService = auditService;
    }

    @KafkaListener(topics = "shipments.events", groupId = "rutaexpress-audit")
    public void consume(String event) {
        auditService.record(null, "SHIPMENT_EVENT", "kafka", event);
    }
}
