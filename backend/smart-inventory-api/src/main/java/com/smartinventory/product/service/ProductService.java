package com.smartinventory.product.service;

import com.smartinventory.category.service.CategoryService;
import com.smartinventory.common.exception.BadRequestException;
import com.smartinventory.common.exception.ResourceNotFoundException;
import com.smartinventory.product.dto.ProductRequestDTO;
import com.smartinventory.product.dto.ProductResponseDTO;
import com.smartinventory.product.model.Product;
import com.smartinventory.product.model.ProductStatus;
import com.smartinventory.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public List<ProductResponseDTO> list(String search) {
        return productRepository.findByStatusOrderByNameAsc(ProductStatus.ACTIVO).stream()
                .filter(p -> search == null || search.isBlank()
                        || p.getName().toLowerCase().contains(search.toLowerCase())
                        || p.getSku().toLowerCase().contains(search.toLowerCase()))
                .map(this::toDto)
                .toList();
    }

    public ProductResponseDTO create(ProductRequestDTO dto) {
        if (productRepository.existsBySku(dto.sku())) {
            throw new BadRequestException("El SKU ya existe");
        }
        validateStockLimits(dto.minimumStock(), dto.maximumStock());
        Product product = Product.builder()
                .name(dto.name())
                .description(dto.description())
                .sku(dto.sku())
                .barcode(dto.barcode())
                .unitPrice(dto.unitPrice())
                .currentStock(dto.currentStock())
                .minimumStock(dto.minimumStock())
                .maximumStock(dto.maximumStock())
                .category(categoryService.get(dto.categoryId()))
                .status(ProductStatus.ACTIVO)
                .build();
        return toDto(productRepository.save(product));
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        validateStockLimits(dto.minimumStock(), dto.maximumStock());
        Product product = get(id);
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setBarcode(dto.barcode());
        product.setUnitPrice(dto.unitPrice());
        product.setMinimumStock(dto.minimumStock());
        product.setMaximumStock(dto.maximumStock());
        product.setCategory(categoryService.get(dto.categoryId()));
        return toDto(productRepository.save(product));
    }

    public List<ProductResponseDTO> lowStock() {
        return productRepository.findAll().stream()
                .filter(p -> p.getCurrentStock() <= p.getMinimumStock())
                .sorted(Comparator.comparing(Product::getCurrentStock))
                .map(this::toDto)
                .toList();
    }

    public Product get(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
    }

    public Product getBySku(String sku) {
        return productRepository.findBySku(sku).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado por SKU"));
    }

    public ProductResponseDTO toDto(Product product) {
        return new ProductResponseDTO(
                product.getId(), product.getName(), product.getDescription(), product.getSku(), product.getBarcode(),
                product.getUnitPrice(), product.getCurrentStock(), product.getMinimumStock(), product.getMaximumStock(),
                product.getCategory().getId(), product.getCategory().getName(), product.getStatus(), product.getCreatedAt());
    }

    private void validateStockLimits(int minimum, int maximum) {
        if (minimum > maximum) {
            throw new BadRequestException("El stock minimo no puede ser mayor que el stock maximo");
        }
    }
}
