package cl.rutaexpress.audit.controller;

import cl.rutaexpress.audit.model.AuditEvent;
import cl.rutaexpress.audit.model.AuditEventType;
import cl.rutaexpress.audit.model.AuditFilter;
import cl.rutaexpress.audit.service.AuditService;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/events")
    public List<AuditEvent> search(
            @RequestParam(required = false) String actor,
            @RequestParam(required = false) AuditEventType type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
            @RequestParam(required = false) String source) {
        return auditService.search(new AuditFilter(actor, type, from, to, source));
    }
}
