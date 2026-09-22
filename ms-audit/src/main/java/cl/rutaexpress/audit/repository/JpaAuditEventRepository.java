package cl.rutaexpress.audit.repository;

import cl.rutaexpress.audit.model.AuditEvent;
import cl.rutaexpress.audit.model.AuditEventEntity;
import cl.rutaexpress.audit.model.AuditFilter;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class JpaAuditEventRepository implements AuditEventRepository, AuditEventQueryRepository {
    private final SpringDataAuditRepository dataRepository;

    public JpaAuditEventRepository(SpringDataAuditRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Override
    public AuditEvent save(AuditEvent event) {
        return dataRepository.save(new AuditEventEntity(event)).toModel();
    }

    @Override
    public List<AuditEvent> findByShipmentId(UUID shipmentId) {
        return dataRepository.findByShipmentIdOrderByTimestampAsc(shipmentId).stream()
                .map(AuditEventEntity::toModel).toList();
    }

    @Override
    public List<AuditEvent> search(AuditFilter filter) {
        return dataRepository.findAll().stream().map(AuditEventEntity::toModel)
                .filter(filter::matches).toList();
    }

}
