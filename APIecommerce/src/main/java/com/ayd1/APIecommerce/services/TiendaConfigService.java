/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services;

import com.ayd1.APIecommerce.models.TiendaConfig;
import com.ayd1.APIecommerce.repositories.TiendaConfigReporitory;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Luis Monterroso
 */
@org.springframework.stereotype.Service
public class TiendaConfigService extends Service {

    @Autowired
    private TiendaConfigReporitory tiendaConfigReporitory;

    public TiendaConfig getTiendaConfig() throws Exception {
        Optional<TiendaConfig> search = this.tiendaConfigReporitory.findFirstByOrderByIdAsc();
        if (search.isEmpty()) {
            throw new Exception("Configuraciones no encontradas");
        }
        return search.get();
    }

}
