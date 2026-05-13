package com.smartinventory.category.service;

import com.smartinventory.audit.service.AuditService;
import com.smartinventory.category.dto.CategoryRequestDTO;
import com.smartinventory.category.dto.CategoryResponseDTO;
import com.smartinventory.category.model.Category;
import com.smartinventory.category.repository.CategoryRepository;
import com.smartinventory.common.exception.BadRequestException;
import com.smartinventory.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AuditService auditService;

    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> list() {
        return categoryRepository.findByActiveTrueOrderByNameAsc().stream().map(this::toDto).toList();
    }

    @Transactional
    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        if (categoryRepository.existsByNameIgnoreCase(dto.name())) {
            throw new BadRequestException("La categoria ya existe");
        }
        Category saved = categoryRepository.save(Category.builder().name(dto.name()).description(dto.description()).build());
        auditService.log("CREATE", "Category", saved.getId(), "Categoria creada: " + saved.getName());
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public Category get(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));
    }

    public CategoryResponseDTO toDto(Category category) {
        return new CategoryResponseDTO(category.getId(), category.getName(), category.getDescription(), category.isActive(), category.getCreatedAt());
    }
}
