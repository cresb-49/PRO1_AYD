/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services.reportes.imprimibles;

import com.ayd1.APIecommerce.models.dto.reports.ReporteInventarioDto;
import com.ayd1.APIecommerce.services.reportes.Reporte;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class ReporteInventarioImprimible extends Reporte {

    private ReporteInventarioDto reporteDatos;

    public byte[] init(ReporteInventarioDto reporteDatos,
            String formatoExportar) throws Exception {
        this.reporteDatos = reporteDatos;
        //si pasaron las comporbaciones mandamos a traer los parametros
        Map<String, Object> parametrosReporte = this.construirReporteInventario();
        //mandamos ha abrir el reporte
        return this.exportarReporte("ReporteInventario", parametrosReporte,
                formatoExportar);
    }

    private Map<String, Object> construirReporteInventario() throws Exception {
        //crear el mapa que contendra los parametros del reporte
        Map<String, Object> parametrosReporte = new HashMap<>();

        //creamos un nuevo JRBeanArrayDataSource (necesario para los datos de la tabla del reporte)
        JRBeanArrayDataSource tablaPocoStock
                = new JRBeanArrayDataSource(
                        this.reporteDatos.getProductosConBajaExistencia().toArray());
        JRBeanArrayDataSource tablaStock
                = new JRBeanArrayDataSource(
                        this.reporteDatos.getRestablecimientos().toArray());

        //anadimos los parametros al map (la key debe llamarse exactamente como los prameters en el reporte)
        parametrosReporte.put("tablaPocoStock", tablaPocoStock);

        parametrosReporte.put("tablaStock", tablaStock);

        parametrosReporte.put("fechaGeneracion",
                this.manejadorDeFecha.parsearFechaYHoraAFormatoRegional(
                        Instant.now()));
        return parametrosReporte;
    }
}
