/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models.dto;

import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class TiendaConfigDto {

    private String nombreTienda;

    private String imagenTiendaString = "http://localhost:8080/api/tienda_config/public/getImagenTienda";

    private Double precioPagoContraEntrega;

    private String direccionEmpresa;

    private Double costoEnvio;

    public TiendaConfigDto() {
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public String getImagenTiendaString() {
        return imagenTiendaString;
    }

    public void setImagenTiendaString(String imagenTiendaString) {
        this.imagenTiendaString = imagenTiendaString;
    }

    public Double getPrecioPagoContraEntrega() {
        return precioPagoContraEntrega;
    }

    public void setPrecioPagoContraEntrega(Double precioPagoContraEntrega) {
        this.precioPagoContraEntrega = precioPagoContraEntrega;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public Double getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(Double costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

}
