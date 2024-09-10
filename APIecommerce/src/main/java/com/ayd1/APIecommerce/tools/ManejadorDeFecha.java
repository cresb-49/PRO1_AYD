/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.tools;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class ManejadorDeFecha {

    /**
     * Convierte un Local date ha un formato de fecha recional en dd/MM/yyyy
     *
     * @param instante
     * @return
     */
    public String parsearFechaYHoraAFormatoRegional(Instant instante) {
        if (instante == null) { // si la fecha es nula retornamos vacío
            return "-";
        }
        /* Convertimos Instant a LocalDateTime utilizando la zona horaria del sistema */
        LocalDateTime fechaLocal = LocalDateTime.ofInstant(instante,
                ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaLocal.format(formatter);
        return fechaFormateada;
    }

    /**
     * Convierte un Local date ha un formato de fecha recional en dd/MM/yyyy
     *
     * @param instante
     * @return
     */
    public String parsearFechaYHoraAFormatoRegional(LocalDate instante) {
        if (instante == null) { // si la fecha es nula retornamos vacío
            return "-";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = instante.format(formatter);
        return fechaFormateada;
    }

    public LocalDate convertStringToLocalDate(String dateString) {
        if (dateString == null) {
            return null;
        }
        // Convertir el string a LocalDate
        return LocalDate.parse(dateString);
    }

    public Instant convertStringToInstant(String dateString) {
        if (dateString == null) {
            return null;
        }
        // Convertir el string a LocalDate
        LocalDate localDate = LocalDate.parse(dateString);

        // Convertir LocalDate a Instant usando la zona horaria del sistema
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

}
