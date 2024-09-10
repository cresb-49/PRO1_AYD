/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services.reportes.imprimibles;

import com.ayd1.APIecommerce.models.dto.reports.ReporteVentasDto;
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
public class ReporteDeVentasImprimible extends Reporte {

    private ReporteVentasDto reporteDatos;

    public byte[] init(ReporteVentasDto reporteDatos,
            String formatoExportar) throws Exception {
        this.reporteDatos = reporteDatos;
        //si pasaron las comporbaciones mandamos a traer los parametros
        Map<String, Object> parametrosReporte = this.construirReporteDeVentas();
        //mandamos ha abrir el reporte
        return this.exportarReporte("ReporteVentas", parametrosReporte,
                formatoExportar);
    }

    private Map<String, Object> construirReporteDeVentas() throws Exception {
        //crear el mapa que contendra los parametros del reporte
        Map<String, Object> parametrosReporte = new HashMap<>();

        //creamos un nuevo JRBeanArrayDataSource (necesario para los datos de la tabla del reporte) a partir del Set
        JRBeanArrayDataSource tablaDesgloce
                = new JRBeanArrayDataSource(
                        this.reporteDatos.getProductosVendidos().toArray());

        //anadimos los parametros al map (la key debe llamarse exactamente como los prameters en el reporte)
        parametrosReporte.put("tablaDesgloce", tablaDesgloce);

        parametrosReporte.put("total", "Q" + this.reporteDatos.getTotal());

        parametrosReporte.put("noVentas",
                this.reporteDatos.getNoVentas());
        parametrosReporte.put("totalPagoEntrega",
                "Q" + this.reporteDatos.getTotalPagoEntrega());

        parametrosReporte.put("fecha1",
                this.reporteDatos.getFecha1());

        parametrosReporte.put("fecha2",
                this.reporteDatos.getFecha2());

        parametrosReporte.put("fechaGeneracion",
                this.manejadorDeFecha.parsearFechaYHoraAFormatoRegional(
                        Instant.now()));

        parametrosReporte.put("totalImpuestoPagado",
                "Q" + this.reporteDatos.getTotalImpuestoPagado());

        parametrosReporte.put("totalImpuestoPagado",
                "Q" + this.reporteDatos.getTotalImpuestoPagado());
        return parametrosReporte;
    }
}
