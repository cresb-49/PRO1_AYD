/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class ReporteExportRequest extends ReporteRequest {

    @NotBlank(message = "El nombre de la categoria no puede estar vacío.")
    @NotNull(message = "El nombre de la categoria no puede ser nulo")
    @Pattern(regexp = "^(reporteVentas|reporteInventario|reporteClientesFrecuentes"
            + "|reportePedidos)$",
            message = "El nombre del reporte debe ser "
            + "uno de los valores permitidos: reporteVentas,"
            + " reporteInventario, reporteClientesFrecuentes, reportePedidos.")
    private String tipoReporte;
    @NotBlank(message = "El nombre de la categoria no puede estar vacío.")
    @NotNull(message = "El nombre de la categoria no puede ser nulo")
    @Pattern(regexp = "^(excel|pdf|word)$",
            message = "El tipo de exporte debe ser uno "
            + "de los valores permitidos: excel, pdf, word.")
    private String tipoExporte;

    public ReporteExportRequest(String tipoReporte, String fecha1, String fecha2,
            String tipoExporte) {
        super(fecha1, fecha2);
        this.tipoReporte = tipoReporte;
        this.tipoExporte = tipoExporte;
    }

    public ReporteExportRequest() {
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public String getTipoExporte() {
        return tipoExporte;
    }

    public void setTipoExporte(String tipoExporte) {
        this.tipoExporte = tipoExporte;
    }

}
