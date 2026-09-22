package cl.rutaexpress.audit;

import cl.rutaexpress.audit.model.AuditEvent;
import cl.rutaexpress.audit.service.AuditService;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audit")
public class AuditController {
    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/shipments/{shipmentId}/timeline")
    public List<AuditEvent> timeline(@PathVariable UUID shipmentId) {
        return auditService.findTimeline(shipmentId);
    }
}
