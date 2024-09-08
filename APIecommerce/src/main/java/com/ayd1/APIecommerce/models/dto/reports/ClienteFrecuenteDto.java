/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models.dto.reports;

/**
 *
 * @author Luis Monterroso
 */
public class ClienteFrecuenteDto {

    private Long id;
    private String nombres;
    private String apellidos;
    private Long numeroPedidos;
    private Double valorTotalCompras;
    private Double ticketPromedio;

    public ClienteFrecuenteDto() {
    }

    public ClienteFrecuenteDto(Long id, String nombres, String apellidos, Long numeroPedidos, Double valorTotalCompras, Double ticketPromedio) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.numeroPedidos = numeroPedidos;
        this.valorTotalCompras = valorTotalCompras;
        this.ticketPromedio = ticketPromedio;
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

    public Long getNumeroPedidos() {
        return numeroPedidos;
    }

    public void setNumeroPedidos(Long numeroPedidos) {
        this.numeroPedidos = numeroPedidos;
    }

    public Double getValorTotalCompras() {
        return valorTotalCompras;
    }

    public void setValorTotalCompras(Double valorTotalCompras) {
        this.valorTotalCompras = valorTotalCompras;
    }

    public Double getTicketPromedio() {
        return ticketPromedio;
    }

    public void setTicketPromedio(Double ticketPromedio) {
        this.ticketPromedio = ticketPromedio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
