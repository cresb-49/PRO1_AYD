/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.tools.mappers;

import com.ayd1.APIecommerce.models.Producto;
import com.ayd1.APIecommerce.models.dto.ProductoDto;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Luis Monterroso
 */
@Mapper
public interface ProductoMapper {

    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

    public ProductoDto productoToProductoDto(Producto producto);

    public Producto productoDtoToProducto(ProductoDto productoDto);

    @IterableMapping(elementTargetType = ProductoDto.class)
    public List<ProductoDto> productosToProductoDtos(List<Producto> productos);
}
