/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ayd1.APIecommerce.repositories;

import com.ayd1.APIecommerce.models.Envio;
import com.ayd1.APIecommerce.models.dto.reports.EnvioReporteDto;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Luis Monterroso
 */
@Repository
public interface EnvioRepository extends CrudRepository<Envio, Long> {

    @Override
    public List<Envio> findAll();

    public List<Envio> findAllByEstadoEnvio_Nombre(String nombre);

    public List<Envio> findAllByCreatedAtBetween(Instant fechaInicio, Instant fechaFin);

}
