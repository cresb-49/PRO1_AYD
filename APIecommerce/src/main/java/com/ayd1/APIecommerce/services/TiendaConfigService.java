/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services;

import com.ayd1.APIecommerce.models.TiendaConfig;
import com.ayd1.APIecommerce.models.dto.TiendaConfigDto;
import com.ayd1.APIecommerce.repositories.TiendaConfigReporitory;
import com.ayd1.APIecommerce.tools.mappers.TiendaConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Luis Monterroso
 */
@org.springframework.stereotype.Service
public class TiendaConfigService extends Service {
    
    @Autowired
    private TiendaConfigReporitory tiendaConfigReporitory;

    /**
     * Busca la configuracion de la tienda y la devuelve SE RECOMIENDA USARLO
     * SOLO EN BACKEND (POR LA IMAGEN)
     *
     * @return
     * @throws Exception
     */
    public TiendaConfig getTiendaConfig() throws Exception {
        TiendaConfig search = this.tiendaConfigReporitory.findFirstByOrderByIdAsc().orElse(null);
        if (search == null) {
            throw new Exception("Configuraciones no encontradas");
        }
        return search;
    }

    /**
     * Busca la configuracion de la tienda y la devuelve
     *
     * @return
     * @throws Exception
     */
    public TiendaConfigDto getTiendaConfigDto() throws Exception {
        TiendaConfig search = this.tiendaConfigReporitory.findFirstByOrderByIdAsc().orElse(null);
        if (search == null) {
            throw new Exception("Configuraciones no encontradas");
        }
        return TiendaConfigMapper.INSTANCE.tiendaConfigToTiendaConfigDto(search);
    }

    /**
     * Busca la configuracion de la tienda y actualiza los dato segun la nueva
     * configuracion
     *
     * @param config
     * @return
     * @throws Exception
     */
    public TiendaConfigDto actualizarTiendaConfig(TiendaConfig config) throws Exception {
        //validamos el objeto
        this.validar(config);
        //traemos la configuracion de la tienda
        TiendaConfig search = this.tiendaConfigReporitory.findFirstByOrderByIdAsc().orElse(null);
        
        if (search == null) {
            throw new Exception("Configuraciones no encontradas");
        }

        //seteamos la id de la configuracion al objeto d eactualizacion
        config.setId(search.getId());
        config.setImagenTienda(search.getImagenTienda());//evitamos la actualizacion de la imagen
        config.setMimeTypeImg(search.getMimeTypeImg());
        //guardamos la info
        TiendaConfig save = this.tiendaConfigReporitory.save(config);
        
        return TiendaConfigMapper.INSTANCE.tiendaConfigToTiendaConfigDto(save);
    }

    /**
     * Busca la configuracion de la tienda y actualiza la imagen de la tienda
     *
     * @param file
     * @return
     * @throws Exception
     */
    public TiendaConfigDto actualizarImagenDeLaTienda(
            MultipartFile file) throws Exception {
        if (file == null) {
            throw new Exception("Imagen vacia.");
        }
        //traemos la configuracion de la tienda
        TiendaConfig search = this.tiendaConfigReporitory.findFirstByOrderByIdAsc().orElse(null);
        
        if (search == null) {
            throw new Exception("Configuraciones no encontradas");
        }
        
        search.setImagenTienda(file.getBytes());
        search.setMimeTypeImg(file.getContentType());

        //guardamos la info
        TiendaConfig save = this.tiendaConfigReporitory.save(search);
        
        return TiendaConfigMapper.INSTANCE.tiendaConfigToTiendaConfigDto(save);
    }
}
