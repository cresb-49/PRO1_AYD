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
public class EnvioReporteDto {

    private Long ventaId;
    private String estadoEnvio;
    private String direccionEnvio;
    private Integer cantidadProductos;
    private List<DesgloceDto> desgloce;

    public EnvioReporteDto() {
    }

    public EnvioReporteDto(Long ventaId, String estadoEnvio, String direccionEnvio, Integer cantidadProductos, List<DesgloceDto> desgloce) {
        this.ventaId = ventaId;
        this.estadoEnvio = estadoEnvio;
        this.direccionEnvio = direccionEnvio;
        this.cantidadProductos = cantidadProductos;
        this.desgloce = desgloce;
    }

    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }

    public String getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(String estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public Integer getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(Integer cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public List<DesgloceDto> getDesgloce() {
        return desgloce;
    }

    public void setDesgloce(List<DesgloceDto> desgloce) {
        this.desgloce = desgloce;
    }

}
