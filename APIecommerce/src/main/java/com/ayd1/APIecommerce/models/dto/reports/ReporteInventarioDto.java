/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models.dto.reports;

import com.ayd1.APIecommerce.models.Producto;
import com.ayd1.APIecommerce.models.dto.ProductoDto;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class ReporteInventarioDto {

    private List<ProductoDto> productosConBajaExistencia;
    private List<RestablecimientoStock> restablecimientos;

    public ReporteInventarioDto() {
    }

    public ReporteInventarioDto(List<ProductoDto> productosConBajaExistencia, List<RestablecimientoStock> restablecimientos) {
        this.productosConBajaExistencia = productosConBajaExistencia;
        this.restablecimientos = restablecimientos;
    }

    public List<ProductoDto> getProductosConBajaExistencia() {
        return productosConBajaExistencia;
    }

    public void setProductosConBajaExistencia(List<ProductoDto> productosConBajaExistencia) {
        this.productosConBajaExistencia = productosConBajaExistencia;
    }

    public List<RestablecimientoStock> getRestablecimientos() {
        return restablecimientos;
    }

    public void setRestablecimientos(List<RestablecimientoStock> restablecimientos) {
        this.restablecimientos = restablecimientos;
    }

}
