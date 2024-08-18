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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombres", length = 250, unique = false)
    @NotBlank(message = "El nombre del cliente no puede estar vacío.")
    @Size(min = 1, max = 250, message = "El nombre del cliente debe tener entre 1 y 250 caracteres.")
    private String nombres;

    @Column(name = "apellidos", length = 250, unique = false)
    @NotBlank(message = "Los apellidos del cliente no puede estar vacío.")
    @NotNull(message = "Los apellidos del cliente no puede ser nulo")
    @Size(min = 1, max = 250, message = "Los apellidos del cliente debe tener entre 1 y 250 caracteres.")
    private String apellidos;

    @Column(name = "email", length = 250, unique = true)
    @NotBlank(message = "El email del cliente no puede estar vacío.")
    @NotNull(message = "El email del cliente no puede ser nulo")
    @Size(min = 1, max = 250, message = "El email del cliente debe tener entre 1 y 250 caracteres.")
    private String email;

    @Column(name = "nit", length = 250, unique = true)
    private String nit;

    @Column(name = "password", length = 250, unique = false)
    @NotBlank(message = "La password del cliente no puede estar vacía.")
    @NotNull(message = "La password del cliente no puede ser nula.")
    @Size(min = 1, max = 250, message = "El email del cliente debe tener entre 1 y 250 caracteres.")
    private String password;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "usuario", orphanRemoval = true)//indicamos que la relacion debera ser por medio del atributo "Paciente" del objeto Tratamiento
    @Cascade(CascadeType.ALL)
    private List<UsuarioRol> roles;

    /**
     * Para Spring
     *
     * @param id
     * @param nombres
     * @param apellidos
     * @param email
     * @param nit
     * @param password
     * @param createdAt
     * @param updatedAt
     */
    public Usuario(Long id, String nombres, String apellidos, String email, String nit, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.nit = nit;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Creacion y modificacion
     *
     * @param id
     * @param nombres
     * @param apellidos
     * @param email
     * @param nit
     * @param password
     */
    public Usuario(Long id, String nombres, String apellidos, String email, String nit, String password) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.nit = nit;
        this.password = password;
    }

    /**
     * Eliminacion
     *
     * @param id
     */
    public Usuario(Long id) {
        this.id = id;
    }

    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<UsuarioRol> getRoles() {
        return roles;
    }

    public void setRoles(List<UsuarioRol> roles) {
        this.roles = roles;
    }

}
