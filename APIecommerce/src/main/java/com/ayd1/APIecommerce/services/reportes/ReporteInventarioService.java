/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services.reportes;

import com.ayd1.APIecommerce.models.LineaVenta;
import com.ayd1.APIecommerce.models.Producto;
import com.ayd1.APIecommerce.models.dto.ProductoDto;
import com.ayd1.APIecommerce.models.dto.reports.ReporteInventarioDto;
import com.ayd1.APIecommerce.models.dto.reports.ReporteVentasDto;
import com.ayd1.APIecommerce.models.dto.reports.RestablecimientoStock;
import com.ayd1.APIecommerce.models.request.ReporteExportRequest;
import com.ayd1.APIecommerce.repositories.ProductoRepository;
import com.ayd1.APIecommerce.services.ProductoService;
import com.ayd1.APIecommerce.services.reportes.imprimibles.ReporteInventarioImprimible;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Luis Monterroso
 */
@Service
public class ReporteInventarioService extends Reporte {

    @Autowired
    private ReporteInventarioImprimible inventarioImprimible;
    @Autowired
    private ProductoService productoService;

    public ReporteInventarioDto generarReporteInventario() throws Exception {
        //mandar a traer los productos con baja existencia
        List<ProductoDto> productosConBajaExistencia = this.productoService.getProductosConBajaExistencia();
        //mandamos a traer todos los productos existentes
        List<Producto> productos = this.productoService.getProductos();
        //mandamos a analizar el stock de los productos segun sus ventas
        List<RestablecimientoStock> restablecimientos = this.analizarVentasDeProductos(productos);
        //podemos construir el objeto que representa el reporte
        return new ReporteInventarioDto(productosConBajaExistencia, restablecimientos);
    }

    public byte[] exportarReporteInventario(ReporteExportRequest request) throws Exception {
        //mandamos a construir el reporte
        ReporteInventarioDto reporte = this.generarReporteInventario();
        //enviamos el reporte a JasperReport
        return this.inventarioImprimible.init(reporte,
                request.getTipoExporte());
    }

    /**
     * Por cada producto calcula el numero d eventas promedio que ha tenido el
     * producto a lo largo de la historia
     *
     * @return
     */
    private List<RestablecimientoStock> analizarVentasDeProductos(List<Producto> productos)
            throws Exception {
        List<RestablecimientoStock> restablecimientos = new ArrayList<>();
        for (Producto item : productos) {
            //mandar a traer el prducto con su
            Producto producto = this.productoService.getProducto(item.getId());
            //mandar a calcular el promedio de ventas de cada producto
            Double cantidadVentasPromedio = this.calcularPromedioVentasDiariasPorProducto(
                    producto.getLineaVentas());
            //mandamos a traer la fecha en la cual deberian alimentar el stock
            String fechaAgotamiento = this.estimarFechaAgotamiento(
                    cantidadVentasPromedio,
                    item.getStock());

            restablecimientos.add(new RestablecimientoStock(
                    item.getId(),
                    item.getNombre(),
                    cantidadVentasPromedio,
                    item.getStock(),
                    fechaAgotamiento)
            );
        }
        return restablecimientos;
    }

    /**
     * Calcula el promedio de ventas diaras de un producto en especifico
     *
     * @param lineasVenta las lineas de ventas en donde este producto aparece
     * @return
     */
    private Double calcularPromedioVentasDiariasPorProducto(List<LineaVenta> lineasVenta) {
        // Calcular la cantidad total vendida de este producto
        int cantidadTotal = lineasVenta.stream()
                .mapToInt(LineaVenta::getCantidad)
                .sum();

        // Encontrar la fecha de la primera y última venta
        Instant fechaPrimeraVenta = lineasVenta.stream()
                .map(lineaVenta -> lineaVenta.getVenta().getCreatedAt())
                .min(Instant::compareTo)
                .orElse(Instant.now());

        Instant fechaUltimaVenta = lineasVenta.stream()
                .map(lineaVenta -> lineaVenta.getVenta().getCreatedAt())
                .max(Instant::compareTo)
                .orElse(Instant.now());

        // Calcular la cantidad de días entre la primera y última venta
        long diasEntreVentas = ChronoUnit.DAYS.between(
                fechaPrimeraVenta,
                fechaUltimaVenta) + 1; // +1 para incluir ambos días

        // Calcular el promedio de ventas diarias
        return (double) cantidadTotal / diasEntreVentas;
    }

    private String estimarFechaAgotamiento(Double ventasDiariasPromedio,
            Integer stockActual) {
        if (ventasDiariasPromedio <= 0) {
            return "Sin ventas."; // No se puede calcular si no hay ventas
        }
        int diasRestantes = (int) Math.ceil(stockActual / ventasDiariasPromedio);

        return this.manejadorDeFecha.parsearFechaYHoraAFormatoRegional(
                LocalDate.now().plusDays(diasRestantes));
    }

}
