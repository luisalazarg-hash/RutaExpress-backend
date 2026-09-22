package cl.rutaexpress.audit.model;

import java.time.Instant;

public record AuditFilter(String actor, AuditEventType type, Instant from, Instant to, String source) {
    public boolean matches(AuditEvent event) {
        return (actor == null || actor.equals(event.actor()))
                && (type == null || type.name().equals(event.type()))
                && (source == null || source.equals(event.source()))
                && (from == null || !event.timestamp().isBefore(from))
                && (to == null || !event.timestamp().isAfter(to));
    }
}
