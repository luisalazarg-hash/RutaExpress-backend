package cl.rutaexpress.audit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "audit_events")
public class AuditEventEntity {
    @Id
    private UUID eventId;
    private UUID shipmentId;
    private String type;
    private String actor;
    private Instant timestamp;
    private String source;

    protected AuditEventEntity() {}

    public AuditEventEntity(AuditEvent event) {
        this.eventId = event.eventId();
        this.shipmentId = event.shipmentId();
        this.type = event.type();
        this.actor = event.actor();
        this.timestamp = event.timestamp();
        this.source = event.source();
    }

    public AuditEvent toModel() {
        return new AuditEvent(eventId, shipmentId, type, actor, timestamp, source);
    }

    public UUID getEventId() { return eventId; }
    public UUID getShipmentId() { return shipmentId; }
    public String getType() { return type; }
    public String getActor() { return actor; }
    public Instant getTimestamp() { return timestamp; }
    public String getSource() { return source; }
}
