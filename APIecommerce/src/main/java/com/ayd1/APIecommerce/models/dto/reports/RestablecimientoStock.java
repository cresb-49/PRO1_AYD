/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models.dto.reports;

import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class RestablecimientoStock {

    private Long idR;
    private String nombreProductoR;
    private Double ventasPromedioPorDiaR;
    private Integer stockActualR;
    private String fechaAgotamientoR;

    public RestablecimientoStock(Long id, String nombreProducto,
            Double ventasPromedioPorDia, Integer stockActual,
            String fechaAgotamiento) {
        this.idR = id;
        this.nombreProductoR = nombreProducto;
        this.ventasPromedioPorDiaR = ventasPromedioPorDia;
        this.stockActualR = stockActual;
        this.fechaAgotamientoR = fechaAgotamiento;
    }

    public RestablecimientoStock() {
    }

    public Long getIdR() {
        return idR;
    }

    public void setIdR(Long idR) {
        this.idR = idR;
    }

    public String getNombreProductoR() {
        return nombreProductoR;
    }

    public void setNombreProductoR(String nombreProductoR) {
        this.nombreProductoR = nombreProductoR;
    }

    public Double getVentasPromedioPorDiaR() {
        return ventasPromedioPorDiaR;
    }

    public void setVentasPromedioPorDiaR(Double ventasPromedioPorDiaR) {
        this.ventasPromedioPorDiaR = ventasPromedioPorDiaR;
    }

    public Integer getStockActualR() {
        return stockActualR;
    }

    public void setStockActualR(Integer stockActualR) {
        this.stockActualR = stockActualR;
    }

    public String getFechaAgotamientoR() {
        return fechaAgotamientoR;
    }

    public void setFechaAgotamientoR(String fechaAgotamientoR) {
        this.fechaAgotamientoR = fechaAgotamientoR;
    }

}
