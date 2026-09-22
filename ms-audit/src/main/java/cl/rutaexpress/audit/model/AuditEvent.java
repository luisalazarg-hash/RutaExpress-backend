package cl.rutaexpress.audit.model;

import java.time.Instant;
import java.util.UUID;

public record AuditEvent(UUID eventId, UUID shipmentId, String type, String actor, Instant timestamp, String source) {
    public static AuditEvent create(UUID shipmentId, String type, String actor, String source) {
        return new AuditEvent(UUID.randomUUID(), shipmentId, type, actor, Instant.now(), source);
    }
}
