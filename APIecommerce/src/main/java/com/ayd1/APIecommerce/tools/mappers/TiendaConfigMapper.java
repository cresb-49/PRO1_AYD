/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.tools.mappers;

import com.ayd1.APIecommerce.models.TiendaConfig;
import com.ayd1.APIecommerce.models.dto.TiendaConfigDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Luis Monterroso
 */
@Mapper
public interface TiendaConfigMapper {

    TiendaConfigMapper INSTANCE = Mappers.getMapper(TiendaConfigMapper.class);

    public TiendaConfigDto tiendaConfigToTiendaConfigDto(TiendaConfig producto);
}
