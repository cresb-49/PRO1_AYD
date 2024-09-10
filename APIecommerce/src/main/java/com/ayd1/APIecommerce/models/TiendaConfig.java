/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "tienda_config")
@DynamicUpdate
public class TiendaConfig extends Auditor {

    @Column(name = "nombre_tienda", length = 250, unique = false)
    @NotBlank(message = "El nombre de la tienda no puede estar vacío.")
    @NotNull(message = "El nombre de la tienda no puede ser nulo")
    @Size(min = 1, max = 250, message = "El nombre de la tienda debe tener entre 1 y 250 caracteres.")
    private String nombreTienda;

    @Column(name = "imagen_tienda", nullable = false, length = Integer.MAX_VALUE)
    @Lob
    private byte[] imagenTienda;

    @NotNull(message = "El precio del pago contra entrega de la tienda no puede ser nulo")
    @Min(value = 0, message = "El precio del pago contra entrega de la tienda "
            + "debe tener un valor minimo de 0.")
    private Double precioPagoContraEntrega;

    @NotNull(message = "El costo de envio no puede ser nulo")
    @Min(value = 0, message = "El costo de envio "
            + "debe tener un valor minimo de 0.")
    private Double costoEnvio;

    @Column(length = 250)
    @NotBlank(message = "La direccion de la tienda no puede estar vacía.")
    @NotNull(message = "La direccion de la tienda no puede ser nula.")
    @Size(min = 1, max = 250, message = "La direccion de la tienda debe tener entre 1 y 250 caracteres.")
    private String direccionEmpresa;

    @Column(length = 250)
    private String mimeTypeImg;

    public TiendaConfig(Long id) {
        super(id);
    }

    public TiendaConfig(String nombreTienda, byte[] imagenTienda,
            Double precioPagoContraEntrega, String direccionEmpresa, String mimeTypeImg,
            Double costoEnvio) {
        this.nombreTienda = nombreTienda;
        this.imagenTienda = imagenTienda;
        this.precioPagoContraEntrega = precioPagoContraEntrega;
        this.direccionEmpresa = direccionEmpresa;
        this.mimeTypeImg = mimeTypeImg;
        this.costoEnvio = costoEnvio;
    }

    public TiendaConfig() {
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public byte[] getImagenTienda() {
        return imagenTienda;
    }

    public void setImagenTienda(byte[] imagenTienda) {
        this.imagenTienda = imagenTienda;
    }

    public Double getPrecioPagoContraEntrega() {
        return precioPagoContraEntrega;
    }

    public void setPrecioPagoContraEntrega(Double precioPagoContraEntrega) {
        this.precioPagoContraEntrega = precioPagoContraEntrega;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public String getMimeTypeImg() {
        return mimeTypeImg;
    }

    public void setMimeTypeImg(String mimeTypeImg) {
        this.mimeTypeImg = mimeTypeImg;
    }

    public Double getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(Double costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public String getExtension() {
        if (this.mimeTypeImg.split("/")[1] != null) {
            return this.mimeTypeImg.split("/")[1];
        }
        return "png";
    }
}
