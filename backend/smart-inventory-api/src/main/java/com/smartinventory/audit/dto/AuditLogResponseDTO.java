package com.smartinventory.audit.dto;

import java.time.LocalDateTime;

public record AuditLogResponseDTO(
        Long id,
        String userName,
        String userEmail,
        String action,
        String entity,
        Long entityId,
        String description,
        LocalDateTime createdAt
) {
}
