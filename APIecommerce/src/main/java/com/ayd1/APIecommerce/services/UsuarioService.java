package com.ayd1.APIecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayd1.APIecommerce.models.Usuario;
import com.ayd1.APIecommerce.repositories.UsuarioRepository;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

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
            //return usuarioRepository.save(usuarioExistente);
        }
        return null;
    }

    public String enviarMailDeRecuperacion(String correo) throws Exception {
        if (correo.isBlank()) {//si el correo esta en blanco entonces lanzmaos error
            throw new Exception("Correo vacio.");
        }

        //mandamos a traer el estado de la cuenta
        Optional<Usuario> busquedaUsuario = 
                usuarioRepository.findByCorreoElectronico(correoElectronico);
        if (!busquedaUsuario.isEmpty()) {
            throw new Exception("No hemos encontrado tu correo electrónico.");
        }

        Optional<EstadoCuentaModel> busqueda = estadoCuentaRepository.findByUsuarioId(busquedaUsuario.get().getId());

        //obtenemos el modelo de a busqueda
        EstadoCuentaModel estadoCuenta = busqueda.get();
        //creamos el codigo de recuperacion
        String codigoRecuperacion = UUID.randomUUID().toString();
        //actualizamos el codigo de recuperacion
        estadoCuenta.setCodigoRecuperacion(codigoRecuperacion);
        //actualizamos en la bd
        EstadoCuentaModel actualizacion = (EstadoCuentaModel) estadoCuentaRepository.save(estadoCuenta);
        if (actualizacion.getCodigoRecuperacion().equals(codigoRecuperacion)) {
            //usamos el servicio de mail para mander el correo electronico de recuperacion
            mailSenderService.enviarCorreoEnSegundoPlano(correoElectronico, estadoCuenta.getCodigoRecuperacion(), 2);
            return new ActionResposeModel("Te hemos enviado un correo electrónico con las "
                    + "instrucciones para recuperar tu cuenta MeXpose. Por favor revisa tu bandeja de entrada.", true);
        }

        return new ActionResposeModel("No hemos podido enviar el correo electrónico. Intentalo más tarde.", false);

        return null;
    }
}
