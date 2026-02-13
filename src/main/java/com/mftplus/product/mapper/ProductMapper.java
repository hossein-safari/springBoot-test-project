package com.mftplus.product.mapper;

import com.mftplus.product.dto.ProductDto;
import com.mftplus.product.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")  // Allows Spring to inject this mapper
public interface ProductMapper {

    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDto);
}