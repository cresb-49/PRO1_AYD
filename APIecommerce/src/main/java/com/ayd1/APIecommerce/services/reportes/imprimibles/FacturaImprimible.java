/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services.reportes.imprimibles;

import com.ayd1.APIecommerce.models.DatosFacturacion;
import com.ayd1.APIecommerce.models.LineaVenta;
import com.ayd1.APIecommerce.models.Venta;
import com.ayd1.APIecommerce.models.dto.reports.DesgloceDto;
import com.ayd1.APIecommerce.services.FacturaService;
import com.ayd1.APIecommerce.tools.ManejadorDeFecha;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class FacturaImprimible extends ConstructorImprimible {

    private Venta venta;
    private DatosFacturacion datosFacturacion;
    private List<LineaVenta> lineaVenta;

    @Autowired
    private ManejadorDeFecha manejadorDeFecha;

    public byte[] init(Venta venta, DatosFacturacion datosFacturacion,
            List<LineaVenta> lineaVenta) throws Exception {
        this.venta = venta;
        this.datosFacturacion = datosFacturacion;
        this.lineaVenta = lineaVenta;
        //si pasaron las comporbaciones mandamos a traer los parametros
        Map<String, Object> parametrosReporte = this.construirFactura();
        //mandamos ha abrir el reporte
        return this.generarReporte("FacturaAyD", parametrosReporte);
    }

    private Map<String, Object> construirFactura() throws Exception {
        //crear el mapa que contendra los parametros del reporte
        Map<String, Object> parametrosReporte = new HashMap<>();

        //mandamos a construir el desgloce
        ArrayList<DesgloceDto> desgloce = this.construirDesgloces(
                this.lineaVenta);

        //creamos un nuevo JRBeanArrayDataSource (necesario para los datos de la tabla del reporte) a partir del Set
        JRBeanArrayDataSource tablaDesgloce
                = new JRBeanArrayDataSource(desgloce.toArray());

        //anadimos los parametros al map (la key debe llamarse exactamente como los prameters en el reporte)
        parametrosReporte.put("tablaDesgloce", tablaDesgloce);
        parametrosReporte.put("total", "Q." + venta.getValorTotal());
        parametrosReporte.put("nombreComprador",
                datosFacturacion.getNombre());
        parametrosReporte.put("cuota_pago_entrega",
                "Q." + venta.getCuotaPagContraEntrega());
        parametrosReporte.put("fecha", "Fecha: "
                + this.manejadorDeFecha.parsearFechaYHoraAFormatoRegional(
                        venta.getCreatedAt()));
        parametrosReporte.put("noItems",
                desgloce.size() + " Items");
        parametrosReporte.put("nit",
                datosFacturacion.getNit());
        return parametrosReporte;
    }

    private ArrayList<DesgloceDto> construirDesgloces(List<LineaVenta> desgloce) {
        ArrayList<DesgloceDto> desgloceDtos = new ArrayList<>();
        for (LineaVenta item : desgloce) {
            DesgloceDto dto = new DesgloceDto(
                    item.getCantidad(),
                    "Q." + item.getPrecio(),
                    item.getProducto().getNombre(),
                    item.getProducto().getDescripcion(),
                    "Q." + item.getCantidad() * item.getPrecio(),
                    "Q." + ((item.getProducto().getPorcentajeImpuesto() / 100)
                    * item.getPrecio())
            );

            desgloceDtos.add(dto);
        }
        return desgloceDtos;
    }
}
