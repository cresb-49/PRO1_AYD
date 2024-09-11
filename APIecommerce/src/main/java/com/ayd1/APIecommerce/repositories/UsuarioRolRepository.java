/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ayd1.APIecommerce.repositories;

import com.ayd1.APIecommerce.models.Usuario;
import com.ayd1.APIecommerce.models.UsuarioRol;
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
public interface UsuarioRolRepository extends CrudRepository<UsuarioRol, Long> {

    @Query("SELECT ur.usuario FROM UsuarioRol ur WHERE ur.rol.nombre = :rolNombre")
    List<Usuario> findUsuariosByRolNombre(@Param("rolNombre") String rolNombre);
}
