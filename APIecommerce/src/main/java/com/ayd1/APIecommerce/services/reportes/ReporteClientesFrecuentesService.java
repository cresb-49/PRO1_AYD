/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services.reportes;

import com.ayd1.APIecommerce.models.dto.reports.ClienteFrecuenteDto;
import com.ayd1.APIecommerce.models.dto.reports.ReporteClientesFrecuentesDto;
import com.ayd1.APIecommerce.models.request.ReporteExportRequest;
import com.ayd1.APIecommerce.models.request.ReporteRequest;
import com.ayd1.APIecommerce.repositories.UsuarioRepository;
import com.ayd1.APIecommerce.services.reportes.imprimibles.ReporteClientesFrecuentesImprimible;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Luis Monterroso
 */
@Service
public class ReporteClientesFrecuentesService extends Reporte {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ReporteClientesFrecuentesImprimible reporteClientesFrecuentesImprimible;

    public ReporteClientesFrecuentesDto generarReporteClientesFrecuentes(ReporteRequest reporteRequest) throws Exception {
        //obtener las fechas
        String fecha1 = reporteRequest.getFecha1();
        String fecha2 = reporteRequest.getFecha2();
        //convertir las fechas en instants
        Instant fecha1Instant = this.manejadorDeFecha.convertStringToInstant(fecha1);
        Instant fecha2Instant = this.manejadorDeFecha.convertStringToInstant(fecha2);
        //mandamos a traer el listado de los clientes frecuentes
        List<ClienteFrecuenteDto> clientesFrecuentes
                = this.usuarioRepository.obtenerReporteClientesFrecuentes(
                        fecha1Instant, fecha2Instant);
        //creamos el objeto Dto de reporte de clinetes fecuentes
        ReporteClientesFrecuentesDto clientesFrecuentesDto = new ReporteClientesFrecuentesDto(
                clientesFrecuentes,
                this.manejadorDeFecha.
                        parsearFechaYHoraAFormatoRegional(fecha1Instant),
                this.manejadorDeFecha.
                        parsearFechaYHoraAFormatoRegional(fecha2Instant)
        );
        //podemos construir el objeto que representa el reporte
        return clientesFrecuentesDto;
    }

    public byte[] exportarReporteClientesFrecuentes(ReporteExportRequest request)
            throws Exception {
        //mandamos a construir el reporte
        ReporteClientesFrecuentesDto reporte
                = this.generarReporteClientesFrecuentes(request);
        //enviamos el reporte a JasperReport
        return this.reporteClientesFrecuentesImprimible.init(reporte,
                request.getTipoExporte());
    }
}
