package com.ayd1.APIecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayd1.APIecommerce.models.Usuario;
import com.ayd1.APIecommerce.repositories.UsuarioRepository;
import com.ayd1.APIecommerce.services.tools.MailService;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MailService mailService;

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuario(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Long id, Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
        if (usuarioExistente != null) {
            return usuarioRepository.save(usuarioExistente);
        }
        return null;
    }

    public String enviarMailDeRecuperacion(String correo) throws Exception {
        if (correo.isBlank()) {//si el correo esta en blanco entonces lanzmaos error
            throw new Exception("Correo vacio.");
        }

        //mandamos a traer el estado de la cuenta
        Optional<Usuario> busquedaUsuario
                = usuarioRepository.findByEmail(correo);
        if (!busquedaUsuario.isEmpty()) {
            throw new Exception("No hemos encontrado tu correo electrónico.");
        }
        //obtenemos el modelo
        Usuario usuario = busquedaUsuario.get();
        //creamos el codigo de recuperacion
        String codigoRecuperacion = UUID.randomUUID().toString();
        //actualizamos el codigo de recuperacion
        usuario.setCodigoRecuperacion(codigoRecuperacion);
        //actualizamos en la bd
        Usuario actualizacion = usuarioRepository.save(usuario);

        if (actualizacion.getCodigoRecuperacion().equals(codigoRecuperacion)) {
            //usamos el servicio de mail para mander el correo electronico de recuperacion
            mailService.enviarCorreoEnSegundoPlano(actualizacion.getEmail(),
                    actualizacion.getCodigoRecuperacion(), 2);
            return "Te hemos enviado un correo electrónico con las "
                    + "instrucciones para recuperar tu cuenta MeXpose. Por favor revisa tu bandeja de entrada.";
        }
        throw new Exception("No hemos podido enviar el correo electrónico. Intentalo más tarde.");
    }
}
