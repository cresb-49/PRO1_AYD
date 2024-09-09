/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services.reportes;

import com.ayd1.APIecommerce.models.Envio;
import com.ayd1.APIecommerce.models.dto.reports.DesgloceDto;
import com.ayd1.APIecommerce.models.dto.reports.EnvioReporteDto;
import com.ayd1.APIecommerce.models.dto.reports.ReportePedidoDto;
import com.ayd1.APIecommerce.models.request.ReporteExportRequest;
import com.ayd1.APIecommerce.models.request.ReporteRequest;
import com.ayd1.APIecommerce.repositories.EnvioRepository;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Luis Monterroso
 */
@Service
public class ReportePedidosService extends Reporte {

    @Autowired
    private EnvioRepository envioRepository;

    public ReportePedidoDto generarReporteDePedidos(ReporteRequest reporteRequest) {
        //obtener las fechas
        String fecha1 = reporteRequest.getFecha1();
        String fecha2 = reporteRequest.getFecha2();
        //convertir las fechas en instants
        Instant fecha1Instant = this.manejadorDeFecha.convertStringToInstant(fecha1);
        Instant fecha2Instant = this.manejadorDeFecha.convertStringToInstant(fecha2);
        //mandamos a traer los envios 
        List<Envio> envios = this.envioRepository.findAllByCreatedAtBetween(
                fecha1Instant, fecha2Instant);
        //mandamos a construir los envios EnvioReporteDto
        List<EnvioReporteDto> enviosDto
                = this.construirEnviosReporte(envios);
        //construimos el reporte
        return new ReportePedidoDto(enviosDto,
                this.manejadorDeFecha.
                        parsearFechaYHoraAFormatoRegional(fecha1Instant),
                this.manejadorDeFecha.
                        parsearFechaYHoraAFormatoRegional(fecha2Instant));
    }

    public List<EnvioReporteDto> construirEnviosReporte(
            List<Envio> envios) {
        return envios.stream().map(envio -> {

            List<DesgloceDto> desgloceDtos
                    = envio.getVenta().getLineaVentas().stream()
                            .map(lv -> new DesgloceDto(
                            lv.getCantidad(),
                            "Q" + lv.getPrecio(), // Asegúrate de convertir el precio a String si es necesario
                            lv.getProducto().getNombre(),
                            lv.getProducto().getDescripcion(),
                            "Q" + (lv.getCantidad() * lv.getPrecio()),
                            "Q" + lv.getImpuestoPagado().toString() // Asegúrate de convertir el impuestoPagado a String si es necesario
                    )).collect(Collectors.toList());

            return new EnvioReporteDto(
                    envio.getId(),
                    envio.getEstadoEnvio().getNombre(),
                    envio.getDireccion(),
                    envio.getVenta().getCantidadProductos(),
                    desgloceDtos
            );
        }).collect(Collectors.toList());
    }

    public byte[] exportarReporteDePedidos(ReporteExportRequest request) {
        ReportePedidoDto reporte = this.generarReporteDePedidos(request);
        return null;
    }
}
