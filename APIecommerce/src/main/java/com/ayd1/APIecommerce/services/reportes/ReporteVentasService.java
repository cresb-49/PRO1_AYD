/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services.reportes;

import com.ayd1.APIecommerce.models.LineaVenta;
import com.ayd1.APIecommerce.models.Venta;
import com.ayd1.APIecommerce.models.dto.reports.DesgloceDto;
import com.ayd1.APIecommerce.models.dto.reports.ReporteVentasDto;
import com.ayd1.APIecommerce.models.request.ReporteExportRequest;
import com.ayd1.APIecommerce.models.request.ReporteRequest;
import com.ayd1.APIecommerce.repositories.VentaRepository;
import com.ayd1.APIecommerce.services.reportes.imprimibles.ReporteDeVentasImprimible;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Luis Monterroso
 */
@org.springframework.stereotype.Service
public class ReporteVentasService extends Reporte {

    @Autowired
    private ReporteDeVentasImprimible reporteDeVentasImprimible;
    @Autowired
    private VentaRepository ventaRepository;

    public ReporteVentasDto generarReporteVentas(ReporteRequest request)
            throws Exception {
        String fecha1 = request.getFecha1();
        String fecha2 = request.getFecha2();

        LocalDate fecha1Instant = this.manejadorDeFecha.convertStringToLocalDate(fecha1);
        LocalDate fecha2Instant = this.manejadorDeFecha.convertStringToLocalDate(fecha2);
        //mandamos a traer las ventas desde la fecha1 hasta la fecha2
        List<Venta> ventas = this.ventaRepository.findAllByCreatedAtDateBetween(
                fecha1Instant,
                fecha2Instant
        );
        //mandar a calcular el total de ganancias de esas ventas
        Double total = this.calcularTotalVentas(ventas);
        //calcular el total de ingresos por cuotas de pago contra entrega
        Double totalPagoEntrega = this.calcularTotalPagoEntrega(ventas);
        //calcular el total de impuestos pagados
        Double totalImpuestos = this.calcularImpuestosPagados(ventas);
        //construimos el desgloce de todos los productos vendidos
        List<LineaVenta> lineasDeVentas = this.listarLineaVentas(ventas);
        List<DesgloceDto> productosVendidos = this.construirDesgloces(lineasDeVentas);
        //extraer el total de ventas
        Integer noVentas = productosVendidos.size();
        //creamos el objeto Dto de reporte de ventas
        ReporteVentasDto reporteVentasDto = new ReporteVentasDto(total,
                noVentas, totalPagoEntrega, productosVendidos,
                this.manejadorDeFecha.
                        parsearFechaYHoraAFormatoRegional(fecha1Instant),
                this.manejadorDeFecha.
                        parsearFechaYHoraAFormatoRegional(fecha2Instant),
                totalImpuestos
        );

        return reporteVentasDto;
    }

    public byte[] exportarReporteVentas(ReporteExportRequest request) throws Exception {
        //generamos el reporte de ventas
        ReporteVentasDto reporte = this.generarReporteVentas(request);
        //mandamos a construir el reporte de ventas Jasper
        return this.reporteDeVentasImprimible.init(
                reporte, request.getTipoExporte());
    }

    private Double calcularTotalVentas(List<Venta> ventas) {
        Double total = 0.0;
        for (Venta item : ventas) {
            total += item.getValorTotal();
        }
        return total;
    }

    private Double calcularTotalPagoEntrega(List<Venta> ventas) {
        Double total = 0.0;
        for (Venta item : ventas) {
            total += item.getCuotaPagContraEntrega();
        }
        return total;
    }

    private List<LineaVenta> listarLineaVentas(List<Venta> ventas) {
        List<LineaVenta> lineaVentas = new ArrayList<>();
        for (Venta item : ventas) {
            lineaVentas.addAll(item.getLineaVentas());
        }
        return lineaVentas;
    }

    private Double calcularImpuestosPagados(List<Venta> ventas) {
        Double total = 0.0;
        for (Venta item : ventas) {
            total += item.getTotalImpuestosPagados();
        }
        return total;
    }

}
