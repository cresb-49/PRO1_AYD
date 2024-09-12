/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.ayd1.APIecommerce.enums;

/**
 *
 * @author Luis Monterroso
 */
public enum PermisoEnum {

        CREAR_PRODUCTO("Crear productos",
                        "/api/producto/protected/crearProducto"),
        EDITAR_PRODUCTO("Editar productos",
                        "/api/producto/protected/actualizarProducto"),
        EDITAR_PRODUCTO_IMGS("Editar imagenes de los productos",
                        "/api/producto/protected/actualizarImgProd"),
        ELIMINAR_PRODUCTO("Eliminar productos",
                        "/api/producto/protected/eliminarProducto/"),
        /* Envios */
        GET_ENVIO("Ver detalles de un envio", "/api/envio/protected/getEnvio/"),
        GET_ENVIOS("Ver envios", "/api/envio/protected/getEnvios"),
        CAMBIAR_ESTADO_ENVIO("Cambiar el estado de los envios", "/api/envio/protected/cambiarEstadoEnvio/"),
        /* REPORTES */
        GENERAR_VENTAS("Generar reporte de ventas",
                        "/api/reporte/protected/generarReporteVentas"),
        GENERAR_INVENTARIO("Generar reporte de inventario",
                        "/api/reporte/protected/generarReporteInventario"),
        GENERAR_CLIENTES("Generar reporte de clientes",
                        "/api/reporte/protected/generarReporteClientesFrecuentes"),
        GENERAR_PEDIDOS("Generar reporte de pedidos",
                        "/api/reporte/protected/generarReportePedidos"),
        /* EXPORTACIONES */
        EXPORTAR_VENTAS("Exportar reporte de ventas",
                        "/api/reporte/protected/exportar/reporteVentas"),
        EXPORTAR_INVENTARIO("Exportar reporte de inventario",
                        "/api/reporte/protected/exportar/reporteInventario"),
        EXPORTAR_CLIENTES("Exportar reporte de clientes",
                        "/api/reporte/protected/exportar/reporteClientesFrecuentes"),
        EXPORTAR_PEDIDOS("Exportar reporte de pedidos",
                        "/api/reporte/protected/exportar/reportePedidos"),;

        private final String nombrePermiso;
        private final String ruta;

        private PermisoEnum(String nombrePermiso, String ruta) {
                this.nombrePermiso = nombrePermiso;
                this.ruta = ruta;
        }

        @Override
        public String toString() {
                return this.ruta;
        }

        public String getRuta() {
                return this.ruta;
        }

        public String getNombrePermiso() {
                return nombrePermiso;
        }

}
