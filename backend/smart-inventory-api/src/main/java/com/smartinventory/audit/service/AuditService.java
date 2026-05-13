package com.smartinventory.audit.service;

import com.smartinventory.audit.dto.AuditLogResponseDTO;
import com.smartinventory.audit.model.AuditLog;
import com.smartinventory.audit.repository.AuditLogRepository;
import com.smartinventory.user.model.User;
import com.smartinventory.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<AuditLogResponseDTO> recent() {
        return auditLogRepository.findTop100ByOrderByCreatedAtDesc().stream().map(this::toDto).toList();
    }

    @Transactional
    public void log(String action, String entity, Long entityId, String description) {
        auditLogRepository.save(AuditLog.builder()
                .user(currentUserOrNull())
                .action(action)
                .entity(entity)
                .entityId(entityId)
                .description(description)
                .build());
    }

    private User currentUserOrNull() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null || "anonymousUser".equals(authentication.getName())) {
            return null;
        }
        return userRepository.findByEmail(authentication.getName()).orElse(null);
    }

    private AuditLogResponseDTO toDto(AuditLog log) {
        User user = log.getUser();
        return new AuditLogResponseDTO(
                log.getId(),
                user == null ? "Sistema" : user.getFullName(),
                user == null ? null : user.getEmail(),
                log.getAction(),
                log.getEntity(),
                log.getEntityId(),
                log.getDescription(),
                log.getCreatedAt()
        );
    }
}
