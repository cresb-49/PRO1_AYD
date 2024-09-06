/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models.dto.reports;

import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class ReporteVentasDto {

    private Double total;
    private Integer noVentas;
    private Double totalPagoEntrega;
    private List<DesgloceDto> productosVendidos;
    private String fecha1;
    private String fecha2;
    private Double totalImpuestoPagado;

    public ReporteVentasDto(Double total, Integer noVentas, Double totalPagoEntrega, List<DesgloceDto> productosVendidos, String fecha1, String fecha2, Double totalImpuestoPagado) {
        this.total = total;
        this.noVentas = noVentas;
        this.totalPagoEntrega = totalPagoEntrega;
        this.productosVendidos = productosVendidos;
        this.fecha1 = fecha1;
        this.fecha2 = fecha2;
        this.totalImpuestoPagado = totalImpuestoPagado;
    }

    public ReporteVentasDto() {
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getNoVentas() {
        return noVentas;
    }

    public void setNoVentas(Integer noVentas) {
        this.noVentas = noVentas;
    }

    public Double getTotalPagoEntrega() {
        return totalPagoEntrega;
    }

    public void setTotalPagoEntrega(Double totalPagoEntrega) {
        this.totalPagoEntrega = totalPagoEntrega;
    }

    public List<DesgloceDto> getProductosVendidos() {
        return productosVendidos;
    }

    public void setProductosVendidos(List<DesgloceDto> productosVendidos) {
        this.productosVendidos = productosVendidos;
    }

    public String getFecha1() {
        return fecha1;
    }

    public void setFecha1(String fecha1) {
        this.fecha1 = fecha1;
    }

    public String getFecha2() {
        return fecha2;
    }

    public void setFecha2(String fecha2) {
        this.fecha2 = fecha2;
    }

    public Double getTotalImpuestoPagado() {
        return totalImpuestoPagado;
    }

    public void setTotalImpuestoPagado(Double totalImpuestoPagado) {
        this.totalImpuestoPagado = totalImpuestoPagado;
    }

}
