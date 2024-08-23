package com.ayd1.APIecommerce.controllers;

import com.ayd1.APIecommerce.models.dto.LoginDto;
import com.ayd1.APIecommerce.models.Usuario;
import com.ayd1.APIecommerce.models.request.PasswordChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayd1.APIecommerce.services.UsuarioService;
import com.ayd1.APIecommerce.transformers.ApiBaseTransformer;
import java.util.Map;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario/{id}")
    public ApiBaseTransformer getUsuario(@PathVariable Long id) {
        Object data = usuarioService.getUsuario(id);
        return new ApiBaseTransformer(200, "OK", data, null, null);
    }

    @PostMapping("/usuario")
    public ApiBaseTransformer postMethodName(@RequestBody String entity) {
        return new ApiBaseTransformer(200, "OK", null, null, null);
    }

    @PutMapping("usuario/{id}")
    public ApiBaseTransformer putMethodName(@PathVariable String id, @RequestBody String entity) {
        return new ApiBaseTransformer(200, "OK", null, null, null);
    }

    @DeleteMapping("usuario/{id}")
    public ApiBaseTransformer deleteMethodName(@PathVariable String id) {
        return new ApiBaseTransformer(200, "OK", null, null, null);
    }

    @PostMapping("/usuario/public/recuperarPasswordMail")
    public ApiBaseTransformer
            enviarMailDeRecuperacion(@RequestBody Map<String, Object> requestBody) {

        try {
            String correoElectronico = (String) requestBody.get("correoElectronico");
            String mensaje = usuarioService.enviarMailDeRecuperacion(correoElectronico);
            return new ApiBaseTransformer(HttpStatus.OK.value(), "OK", mensaje,
                    null, null);
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage(),
                    null, null, null);
        }

    }

    @PostMapping("/usuario/public/cambioPassword")
    public ApiBaseTransformer cambiarPassword(@RequestBody PasswordChange requestBody) {
        try {
            String respuesta = usuarioService.cambiarPassword(requestBody);
            return new ApiBaseTransformer(HttpStatus.OK.value(), "OK", respuesta,
                    null, null);
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage(),
                    null, null, null);
        }
    }

    @PostMapping("/usuario/public/login")
    public ApiBaseTransformer login(@RequestBody Usuario login) {
        try {
            LoginDto respuesta = usuarioService.iniciarSesion(login);
            return new ApiBaseTransformer(HttpStatus.OK.value(), "OK", respuesta,
                    null, null);
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage(),
                    null, null, null);
        }
    }

    @PostMapping("/usuario/public/crearUsuario")
    public ApiBaseTransformer crearUsuario(@RequestBody Usuario crear) {
        try {
            String respuesta = usuarioService.crearUsuarioNormal(crear);
            return new ApiBaseTransformer(HttpStatus.OK.value(), "OK", respuesta,
                    null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage(),
                    null, null, null);
        }
    }

    /*
    @PostMapping("/usuario/activarCuentaMail")
    public String enviarMailDeConfirmacion(@RequestBody String correoElectronico) {
        return estadoCuentaService.enviarCorreoDeActivacion(correoElectronico);
    }
    @PostMapping("/usuario/activarCuenta")
    public String activarCuenta(@RequestBody String codigo) {
        return estadoCuentaService.activarCuenta(codigo);
    } */
}
