/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.SQLDelete;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "usuario")
@DynamicUpdate
@SQLDelete(sql = "UPDATE usuario SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Usuario extends Auditor {

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

    @Column(name = "nit", length = 250, unique = false)
    private String nit;

    @Column(name = "password", length = 250, unique = false)
    @NotBlank(message = "La password del cliente no puede estar vacía.")
    @NotNull(message = "La password del cliente no puede ser nula.")
    @Size(min = 1, max = 250, message = "El email del cliente debe tener entre 1 y 250 caracteres.")
    private String password;

    @Column(name = "codigo_activacion", columnDefinition = "LONGTEXT")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private String codigoActivacion;
    @Column(name = "codigo_recuperacion", columnDefinition = "LONGTEXT")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private String codigoRecuperacion;
    @Column(name = "estado_activacion", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private boolean estadoActivacion;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, orphanRemoval = true) // indicamos que la relacion debera
                                                                                    // ser por medio del atributo
                                                                                    // "Paciente" del objeto Tratamiento
    @Cascade(CascadeType.ALL)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<UsuarioRol> roles;
    @OneToMany(mappedBy = "usuario", orphanRemoval = true) // indicamos que la relacion debera ser por medio del
                                                           // atributo "Paciente" del objeto Tratamiento
    @Cascade(CascadeType.ALL)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<UsuarioPermiso> permisos;
    @OneToMany(mappedBy = "usuario", orphanRemoval = true) // indicamos que la relacion debera ser por medio del
                                                           // atributo "Paciente" del objeto Tratamiento
    @Cascade(CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private List<DatosFacturacion> facturas;

    @Column(name = "two_factor_code", length = 250)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String twoFactorCode;

    @Column(name = "two_factor_enabled", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ColumnDefault("false")
    private boolean twoFactorEnabled;

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
    public Usuario(String nombres, String apellidos, String email, String nit, String password, String codigoActivacion,
            String codigoRecuperacion, boolean estadoActivacion) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.nit = nit;
        this.password = password;
        this.codigoActivacion = codigoActivacion;
        this.codigoRecuperacion = codigoRecuperacion;
        this.estadoActivacion = estadoActivacion;
    }

    public Usuario(Long id) {
        super(id);
    }

    public Usuario() {
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

    public String getCodigoActivacion() {
        return codigoActivacion;
    }

    public void setCodigoActivacion(String codigoActivacion) {
        this.codigoActivacion = codigoActivacion;
    }

    public String getCodigoRecuperacion() {
        return codigoRecuperacion;
    }

    public void setCodigoRecuperacion(String codigoRecuperacion) {
        this.codigoRecuperacion = codigoRecuperacion;
    }

    public boolean isEstadoActivacion() {
        return estadoActivacion;
    }

    public void setEstadoActivacion(boolean estadoActivacion) {
        this.estadoActivacion = estadoActivacion;
    }

    public List<UsuarioRol> getRoles() {
        return roles;
    }

    public void setRoles(List<UsuarioRol> roles) {
        this.roles = roles;
    }

    public List<UsuarioPermiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<UsuarioPermiso> permisos) {
        this.permisos = permisos;
    }

    public List<DatosFacturacion> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<DatosFacturacion> facturas) {
        this.facturas = facturas;
    }

    public String getTwoFactorCode() {
        return twoFactorCode;
    }

    public void setTwoFactorCode(String twoFactorCode) {
        this.twoFactorCode = twoFactorCode;
    }

    public boolean isTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public void setTwoFactorEnabled(boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

    /**
     * Metodo para mantener las relaciones de roles, permisos y facturas Para
     * evitar que se eliminen al actualizar Se debe llamar antes de actualizar
     * Se afectan todas las relaciones con orphanRemoval = true
     *
     * @param usuario Es el mismo objeto que se va a actualizar
     */
    public void keepOrphanRemoval(Usuario usuario) {
        this.roles = usuario.getRoles();
        this.permisos = usuario.getPermisos();
        this.facturas = usuario.getFacturas();
    }
}
