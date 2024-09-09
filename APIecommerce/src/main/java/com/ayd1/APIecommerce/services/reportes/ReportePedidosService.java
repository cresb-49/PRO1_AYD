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
import com.ayd1.APIecommerce.services.reportes.imprimibles.ReporteEnviosImprimible;
import java.time.Instant;
import java.time.LocalDate;
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
    @Autowired
    private ReporteEnviosImprimible reporteEnviosImprimible;

    public ReportePedidoDto generarReporteDePedidos(ReporteRequest reporteRequest)
            throws Exception {
        //obtener las fechas
        String fecha1 = reporteRequest.getFecha1();
        String fecha2 = reporteRequest.getFecha2();
        //convertir las fechas en instants
        LocalDate fecha1Local = this.manejadorDeFecha.convertStringToLocalDate(fecha1);
        LocalDate fecha2Local = this.manejadorDeFecha.convertStringToLocalDate(fecha2);
        //mandamos a traer los envios 
        List<Envio> envios = this.envioRepository.findAllByCreatedAtDateBetween(
                fecha1Local, fecha2Local);
        //mandamos a construir los envios EnvioReporteDto
        List<EnvioReporteDto> enviosDto
                = this.construirEnviosReporte(envios);
        //construimos el reporte
        return new ReportePedidoDto(enviosDto,
                this.manejadorDeFecha.
                        parsearFechaYHoraAFormatoRegional(fecha1Local),
                this.manejadorDeFecha.
                        parsearFechaYHoraAFormatoRegional(fecha2Local));
    }

    public byte[] exportarReporteDePedidos(ReporteExportRequest request) throws Exception {
        ReportePedidoDto reporte = this.generarReporteDePedidos(request);
        return this.reporteEnviosImprimible.init(reporte,
                request.getTipoExporte());
    }

    private List<EnvioReporteDto> construirEnviosReporte(
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

}
