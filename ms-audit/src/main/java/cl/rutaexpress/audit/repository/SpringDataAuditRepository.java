package cl.rutaexpress.audit.repository;

import cl.rutaexpress.audit.model.AuditEventEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataAuditRepository extends JpaRepository<AuditEventEntity, UUID> {
    List<AuditEventEntity> findByShipmentIdOrderByTimestampAsc(UUID shipmentId);
}
