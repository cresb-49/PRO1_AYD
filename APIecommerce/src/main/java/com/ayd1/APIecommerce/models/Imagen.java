/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "imagen")
@SQLDelete(sql = "UPDATE imagen SET deleted_at = NULL WHERE id = ?")
public class Imagen extends Auditor {

    @ManyToOne//indicador de relacion muchos a uno
    @JoinColumn(name = "producto", nullable = false) //indicamos que el id del paciente se guardara con un solo field de tabla
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private Producto producto;

    @Column(name = "imagen", nullable = false, length = Integer.MAX_VALUE)
    @Lob
    @NotNull(message = "La imagen del producto no puede ser nula.")
    private byte[] imagen;//imagen que debera indicar la herramienta seleccionada

    @Column(name = "extension", nullable = false)
    private String mimeType;//imagen que debera indicar la herramienta seleccionada

    public Imagen(Long id) {
        super(id);
    }

    public Imagen(Producto producto) {
        this.producto = producto;
    }

    public Imagen() {
    }

    public Imagen(Producto producto, byte[] imagen, String extension) {
        this.producto = producto;
        this.imagen = imagen;
        this.mimeType = extension;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getExtension() {
        if (this.mimeType.split("/")[1] != null) {
            return this.mimeType.split("/")[1];
        }
        return "png";
    }

}
