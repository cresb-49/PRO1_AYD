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
public class ReporteClientesFrecuentesDto {

    List<ClienteFrecuenteDto> clienteFrecuentes;
    private String fecha1;
    private String fecha2;

    public ReporteClientesFrecuentesDto(List<ClienteFrecuenteDto> clienteFrecuentes, String fecha1, String fecha2) {
        this.clienteFrecuentes = clienteFrecuentes;
        this.fecha1 = fecha1;
        this.fecha2 = fecha2;
    }

    public ReporteClientesFrecuentesDto() {
    }

    public List<ClienteFrecuenteDto> getClienteFrecuentes() {
        return clienteFrecuentes;
    }

    public void setClienteFrecuentes(List<ClienteFrecuenteDto> clienteFrecuentes) {
        this.clienteFrecuentes = clienteFrecuentes;
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

}
