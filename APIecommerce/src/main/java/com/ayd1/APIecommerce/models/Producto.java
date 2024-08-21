/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre", length = 250, unique = false)
    @NotBlank(message = "El nombre del producto no puede estar vacío.")
    @NotNull(message = "El nombre del producto no puede ser nulo")
    @Size(min = 1, max = 250, message = "El nombre del producto debe tener entre 1 y 250 caracteres.")
    private String nombre;

    @ManyToOne//indicador de relacion muchos a uno
    @JoinColumn(name = "categoria", nullable = false) //indicamos que el id del paciente se guardara con un solo field de tabla
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Categoria categoria;

    @Column(name = "stock", nullable = false)
    @Min(value = 1, message = "El stock debe tener como valor mínimo 1.")
    private Integer stock;

    @Column(name = "precio", nullable = false)
    @Min(value = 0, message = "El precio debe tener como valor mínimo 0.")
    private Double precio;

    @Column(name = "habilitado", nullable = false)
    private Boolean habilitado;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "producto", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private List<MovimientoStock> movimientos;

    @OneToMany(mappedBy = "producto", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private List<LineaVenta> lineaVentas;

    @OneToMany(mappedBy = "producto", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private List<Imagen> imagenes;

    public Producto() {
    }

    public Producto(Long id, String nombre, Categoria categoria, Integer stock, Double precio, Boolean habilitado) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.stock = stock;
        this.precio = precio;
        this.habilitado = habilitado;
    }


    public Producto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<MovimientoStock> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<MovimientoStock> movimientos) {
        this.movimientos = movimientos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<LineaVenta> getLineaVentas() {
        return lineaVentas;
    }

    public void setLineaVentas(List<LineaVenta> lineaVentas) {
        this.lineaVentas = lineaVentas;
    }

}
