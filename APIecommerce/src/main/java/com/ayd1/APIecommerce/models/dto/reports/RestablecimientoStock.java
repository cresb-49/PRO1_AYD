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

    private String nombreProducto;
    private Double ventasPromedioPorDia;
    private Integer stockActual;
    private String fechaRestablecimientoStock;

    public RestablecimientoStock(String nombreProducto, Double ventasPromedioPorDia, Integer stockActual, String fechaRestablecimientoStock) {
        this.nombreProducto = nombreProducto;
        this.ventasPromedioPorDia = ventasPromedioPorDia;
        this.stockActual = stockActual;
        this.fechaRestablecimientoStock = fechaRestablecimientoStock;
    }

    public RestablecimientoStock() {
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getVentasPromedioPorDia() {
        return ventasPromedioPorDia;
    }

    public void setVentasPromedioPorDia(Double ventasPromedioPorDia) {
        this.ventasPromedioPorDia = ventasPromedioPorDia;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public String getFechaRestablecimientoStock() {
        return fechaRestablecimientoStock;
    }

    public void setFechaRestablecimientoStock(String fechaRestablecimientoStock) {
        this.fechaRestablecimientoStock = fechaRestablecimientoStock;
    }

}
