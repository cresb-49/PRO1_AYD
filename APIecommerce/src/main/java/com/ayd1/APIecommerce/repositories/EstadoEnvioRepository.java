/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ayd1.APIecommerce.repositories;

import com.ayd1.APIecommerce.models.EstadoEnvio;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Luis Monterroso
 */
@Repository
public interface EstadoEnvioRepository extends CrudRepository<EstadoEnvio, Long> {

    Optional<EstadoEnvio> findOneByNombre(String nombre) ;
}
