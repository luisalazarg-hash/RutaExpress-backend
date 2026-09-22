package cl.rutaexpress.audit.repository;

import cl.rutaexpress.audit.model.AuditEvent;
import cl.rutaexpress.audit.model.AuditFilter;
import java.util.List;

public interface AuditEventQueryRepository {
    List<AuditEvent> search(AuditFilter filter);
}
