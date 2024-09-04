/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services.reportes.imprimibles;

import com.ayd1.APIecommerce.models.TiendaConfig;
import com.ayd1.APIecommerce.services.TiendaConfigService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public abstract class ConstructorImprimible {

    @Autowired
    protected TiendaConfigService tiendaConfigService;

    /**
     * Adjunta informacion a un reporte previamente compilado
     *
     * @param reportePath Localizacion del reporte
     * @param parametros Parametros del reporte
     * @return
     * @throws java.lang.Exception
     */
    protected byte[] generarReporte(String reportePath, Map parametros) throws Exception {
        //mandamos a traer la configuracion de la tienda y agregamos el logo y le nombre a los parametros
        TiendaConfig tiendaConfig = this.tiendaConfigService.getTiendaConfig();
        parametros.put("direccion_tienda", tiendaConfig.getDireccionEmpresa());
        parametros.put("nombre_tienda", tiendaConfig.getNombreTienda());
        parametros.put("imagen_tienda",
                new ByteArrayInputStream(tiendaConfig.getImagenTienda()));

        //cargamos el reporte
        JasperReport reporte
                = (JasperReport) JRLoader.loadObject(
                        getClass().getResource("/imprimibles/" + reportePath + ".jasper"));
        //creamos un nuevo Jasper imprimible
        JasperPrint jasperPrint
                = JasperFillManager.fillReport(
                        reporte, parametros, new JREmptyDataSource());

        // Exportamos el reporte a un ByteArrayOutputStream en lugar de un stream directo
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
        // Obtenemos los bytes del pdf
        return out.toByteArray();
    }

}
