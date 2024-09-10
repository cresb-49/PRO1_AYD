/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services.reportes.imprimibles;

import com.ayd1.APIecommerce.models.dto.reports.ReportePedidoDto;
import com.ayd1.APIecommerce.services.reportes.Reporte;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class ReporteEnviosImprimible extends Reporte {

    private ReportePedidoDto reporteDatos;

    public byte[] init(ReportePedidoDto reporteDatos,
            String formatoExportar) throws Exception {
        this.reporteDatos = reporteDatos;
        //si pasaron las comporbaciones mandamos a traer los parametros
        Map<String, Object> parametrosReporte = this.construirReporteDeVentas();
        //mandamos ha abrir el reporte
        return this.exportarReporte("ReportePedidos", parametrosReporte,
                formatoExportar);
    }

    private Map<String, Object> construirReporteDeVentas() throws Exception {
        //crear el mapa que contendra los parametros del reporte
        Map<String, Object> parametrosReporte = new HashMap<>();

        parametrosReporte.put("pedidos",
                this.listarPedidos());
        parametrosReporte.put("fecha1",
                this.reporteDatos.getFecha1());

        parametrosReporte.put("fecha2",
                this.reporteDatos.getFecha2());

        parametrosReporte.put("fechaGeneracion",
                this.manejadorDeFecha.parsearFechaYHoraAFormatoRegional(
                        Instant.now()));
        return parametrosReporte;

    }

    private String listarPedidos() {
        String pedidos = reporteDatos.getEnvios().stream()
                .map(item -> {
                    // Convertir la lista de desglose de productos a un String
                    String productos = item.getDesgloce().stream()
                            .map(producto -> String.format(
                            "Producto: %s, Cantidad: %d, Precio: %s",
                            producto.getProducto(),
                            producto.getCantidad(),
                            producto.getPrecio()
                    )).collect(Collectors.joining("<br>&nbsp;&nbsp;&nbsp;"));

                    // Formatear el String con los datos del envío
                    return String.format(
                            "<b>Id de la venta:</b> %s<br>"
                            + "<b>Direccion:</b> %s<br>"
                            + "<b>Estado del envio:</b> %s<br>"
                            + "<b>Cantidad de productos:</b> %s<br>"
                            + "<b>Productos:</b><br>&nbsp;&nbsp;&nbsp;%s",
                            item.getVentaId(),
                            item.getDireccionEnvio(),
                            item.getEstadoEnvio(),
                            item.getCantidadProductos(),
                            productos
                    );
                })
                .collect(Collectors.joining("<br><br><br>")); // Esto une cada bloque de pedido con doble salto de línea

        return pedidos;
    }
}
