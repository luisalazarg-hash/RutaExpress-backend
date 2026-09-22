package cl.rutaexpress.audit.repository;

import cl.rutaexpress.audit.model.AuditEvent;
import cl.rutaexpress.audit.model.AuditFilter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
public class InMemoryAuditEventRepository implements AuditEventRepository, AuditEventQueryRepository {
    private final CopyOnWriteArrayList<AuditEvent> storage = new CopyOnWriteArrayList<>();
    @Override public AuditEvent save(AuditEvent event) { storage.add(event); return event; }
    @Override public List<AuditEvent> findByShipmentId(UUID shipmentId) { return storage.stream().filter(event -> Objects.equals(event.shipmentId(), shipmentId)).toList(); }
    @Override public List<AuditEvent> search(AuditFilter filter) { return storage.stream().filter(filter::matches).toList(); }
}
