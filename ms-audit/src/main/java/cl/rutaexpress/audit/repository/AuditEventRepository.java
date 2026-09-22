package cl.rutaexpress.audit.repository;

import cl.rutaexpress.audit.model.AuditEvent;
import java.util.List;
import java.util.UUID;

public interface AuditEventRepository {
    AuditEvent save(AuditEvent event);
    List<AuditEvent> findByShipmentId(UUID shipmentId);
}
