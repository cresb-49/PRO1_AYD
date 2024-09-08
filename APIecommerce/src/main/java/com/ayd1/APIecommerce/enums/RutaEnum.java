/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.ayd1.APIecommerce.enums;

/**
 *
 * @author Luis Monterroso
 */
public enum RutaEnum {

    CREAR_PRODUCTO("Crear productos", "tratamientos/TratamientosPage"),
    EDITAR_PRODUCTO("Editar productos", "tratamientos/TratamientosPage"),
    ELIMINAR_PRODUCTO("Eliminar productos", "tratamientos/TratamientosPage"),
    /*REPORTES*/
    GENERAR_VENTAS("exportar", ""),
    GENERAR_INVENTARIO("exportar", ""),
    GENERAR_CLIENTES("exportar", ""),
    /*EXPORTACIONES*/
    EXPORTAR_VENTAS("exportar", ""),
    EXPORTAR_INVENTARIO("exportar", ""),
    EXPORTAR_CLIENTES("exportar", ""),;

    private final String nombrePermiso;
    private final String ruta;

    private RutaEnum(String nombrePermiso, String ruta) {
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
}
