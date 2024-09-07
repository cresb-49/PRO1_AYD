/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services.reportes;

import com.ayd1.APIecommerce.models.LineaVenta;
import com.ayd1.APIecommerce.models.dto.reports.DesgloceDto;
import com.ayd1.APIecommerce.services.reportes.imprimibles.ConstructorReporteImprimible;
import com.ayd1.APIecommerce.tools.ManejadorDeFecha;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class Reporte extends ConstructorReporteImprimible {

    @Autowired
    protected ManejadorDeFecha manejadorDeFecha;

    protected ArrayList<DesgloceDto> construirDesgloces(List<LineaVenta> desgloce) {
        ArrayList<DesgloceDto> desgloceDtos = new ArrayList<>();
        for (LineaVenta item : desgloce) {
            DesgloceDto dto = new DesgloceDto(
                    item.getCantidad(),
                    "Q" + item.getPrecio(),
                    item.getProducto().getNombre(),
                    item.getProducto().getDescripcion(),
                    "Q" + item.getCantidad() * item.getPrecio(),
                    "Q" + item.getImpuestoPagado()
            );

            desgloceDtos.add(dto);
        }
        return desgloceDtos;
    }

    protected Double calcularTotalDeImpuestoPagado(List<LineaVenta> desgloce) {
        Double total = 0.0;
        for (LineaVenta item : desgloce) {
            total += item.getImpuestoPagado();
        }
        return total;
    }
}
