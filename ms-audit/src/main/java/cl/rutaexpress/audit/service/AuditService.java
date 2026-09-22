package cl.rutaexpress.audit.service;

import cl.rutaexpress.audit.model.AuditEvent;
import cl.rutaexpress.audit.model.AuditFilter;
import cl.rutaexpress.audit.repository.AuditEventRepository;
import cl.rutaexpress.audit.repository.AuditEventQueryRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    private final AuditEventRepository repository;
    private final AuditEventQueryRepository queryRepository;
    public AuditService(AuditEventRepository repository, AuditEventQueryRepository queryRepository) {
        this.repository = repository;
        this.queryRepository = queryRepository;
    }
    public AuditEvent record(UUID shipmentId, String type, String actor, String source) { return repository.save(AuditEvent.create(shipmentId, type, actor, source)); }
    public List<AuditEvent> findTimeline(UUID shipmentId) { return repository.findByShipmentId(shipmentId); }
    public List<AuditEvent> search(AuditFilter filter) { return queryRepository.search(filter); }
}
