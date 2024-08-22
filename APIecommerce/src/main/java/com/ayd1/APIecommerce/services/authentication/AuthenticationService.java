/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services.authentication;

import com.ayd1.APIecommerce.models.Usuario;
import com.ayd1.APIecommerce.models.UsuarioRol;
import com.ayd1.APIecommerce.repositories.UsuarioRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author luism
 */
@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioBusqueda = usuarioRepository.findByEmail(username);
        if (!usuarioBusqueda.isEmpty()) {//si no esta vacia la busqueda
            Usuario usuario = usuarioBusqueda.get();
            User.UserBuilder userBuilder = User.withUsername(username);
            ArrayList<String> rolesString = new ArrayList<>();
            for (UsuarioRol item : usuario.getRoles()) {
                rolesString.add(item.getRol().getNombre());
            }
            userBuilder.password(usuario.getPassword()).roles(
                  rolesString.toArray(new String[rolesString.size()])
            );
            return userBuilder.build();

        }
        throw new UsernameNotFoundException(username);
    }

}
