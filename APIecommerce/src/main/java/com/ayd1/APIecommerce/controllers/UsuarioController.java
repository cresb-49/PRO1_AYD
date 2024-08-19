package com.ayd1.APIecommerce.controllers;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
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

    @PostMapping("/usuario/recuperarPasswordMail")
    public ResponseEntity<String>
            enviarMailDeRecuperacion(@RequestBody String correoElectronico) {
        try {
            String mensaje = usuarioService.enviarMailDeRecuperacion(correoElectronico);
            return ResponseEntity.status(HttpStatus.OK).body(mensaje);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ex.getMessage());
        }

    }
    /*
    @PostMapping("/usuario/cambioPassword")
    public String cambiarPassword(@RequestBody PasswordChange cambioPassword) {
        return usuarioService.cambiarPassword(cambioPassword);
    }
     */
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
