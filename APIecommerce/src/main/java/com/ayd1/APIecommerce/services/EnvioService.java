/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services;

import com.ayd1.APIecommerce.models.Envio;
import com.ayd1.APIecommerce.models.EstadoEnvio;
import com.ayd1.APIecommerce.repositories.EnvioRepository;
import com.ayd1.APIecommerce.repositories.EstadoEnvioRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Luis Monterroso
 */
@org.springframework.stereotype.Service
public class EnvioService extends Service {

    @Autowired
    private EnvioRepository envioRepository;
    @Autowired
    private EstadoEnvioRepository estadoEnvioRepository;

    /**
     * Busca el envio por el id y lo devuelve.
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Envio getEnvio(Long id) throws Exception {
        Envio envio = envioRepository.findById(id).orElse(null);

        if (envio == null) {
            throw new Exception("Envio no encontrado");
        }

        return envio;
    }

    /**
     * Busca el envio por el id y lo devuelve.
     *
     * @param id
     * @return
     * @throws Exception
     */
    public List<Envio> getEnvios() throws Exception {
        List<Envio> envios = envioRepository.findAll();
        return envios;
    }

    /**
     * Envia los envios fitrados segun su estado (ENTREGADO, PENDIENTE)
     *
     * @param nombreEstado
     * @return
     */
    public List<Envio> getEnviosFiltradosPorEstado(String nombreEstado) {
        List<Envio> envios = envioRepository.findAllByEstadoEnvio_Nombre(nombreEstado);
        return envios;
    }

    /**
     * Actualiza un envio por medio de su id
     *
     * @param envioActualizar
     * @return
     * @throws Exception
     */
    @Transactional
    public Envio updateEnvio(Long id) throws Exception {

        if (id == null || id <= 0) {
            throw new Exception("Id invalido");
        }

        Envio envio = this.envioRepository.findById(id).orElse(null);

        if (envio == null) {
            throw new Exception("No se encontró el envio para actualizar.");
        }

        EstadoEnvio nuevoEstadoEnvio;

        //Si el estado es entregado entonces pasara a ser pendiente y viseversa
        if (envio.getEstadoEnvio().getNombre().equalsIgnoreCase("entregado")) {
            nuevoEstadoEnvio
                    = this.estadoEnvioRepository.findOneByNombre(
                            "PENDIENTE").orElse(null);
            envio.setEntregadoAt(null);
        } else {
            nuevoEstadoEnvio
                    = this.estadoEnvioRepository.findOneByNombre(
                            "ENTREGADO").orElse(null);
            //marcamos la hora de entrega
            envio.setEntregadoAt(LocalDateTime.now());
        }
        //si el estado es nulo es porque se fallo al hace rla asignaicon entonce slanzamos error
        if (nuevoEstadoEnvio == null) {
            throw new Exception("Error al tratar de asignar el nuevo estado del Envio.");
        }

        //actualizamos el estado de envio
        envio.setEstadoEnvio(nuevoEstadoEnvio);
        //guardamos cambios
        Envio envioActualizado = this.envioRepository.save(envio);

        if (envioActualizado.getId() <= 0) {
            throw new Exception("No se pudo actualizar la categoría");

        }
        return envioActualizado;
    }

}
