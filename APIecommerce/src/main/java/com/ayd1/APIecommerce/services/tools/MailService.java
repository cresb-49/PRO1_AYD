/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services.tools;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ayd1.APIecommerce.models.noBD.AppProperties;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private AppProperties appProperties;

    /**
     *
     * @param correo 1: confirmacion, 2.recuperacion
     * @param codigo
     * @param tipoDeCorreo
     */
    public void enviarCorreoEnSegundoPlano(String correo, String codigo, int tipoDeCorreo) {
        Thread hiloMail = new Thread() {
            @Override
            public void run() {
                switch (tipoDeCorreo) {
                    case 1:
                        enviarCorreoTwoFactorToken(correo, codigo);
                        break;
                    case 2:
                        enviarCorreoDeRecuperacion(correo, codigo);
                        break;
                }
            }
        };
        hiloMail.start();
    }

    private void enviarCorreoDeRecuperacion(String correo, String codigoActivacion) {
        try {
            Context context = new Context();//crear nuevo contexto
            String url = String.format("http://localhost:%s/password_reset/form?c=%s",
                    appProperties.getHostFront1(), codigoActivacion);

            context.setVariable("url", url);//adjuntar las variables     
            String html = templateEngine.process("CorreoDeRecuperacion", context);
            //mandamos el correo electronico
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(html, true);//adjuntamos el mansaje indicando que sera un html
            helper.setTo(correo);
            helper.setSubject("Recuperación de cuenta P1.");
            helper.setFrom("P1 <namenotfound4004@gmail.com>");
            mailSender.send(mimeMessage);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    private void enviarCorreoTwoFactorToken(String correo,String codigo){
        try {
            Context context = new Context();//crear nuevo contexto
            context.setVariable("codigo", codigo);//adjuntar las variables     
            String html = templateEngine.process("CorreoDosPasos", context);
            //mandamos el correo electronico
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(html, true);//adjuntamos el mansaje indicando que sera un html
            helper.setTo(correo);
            helper.setSubject("Token de verificación P1.");
            helper.setFrom("P1 <namenotfound4004@gmail.com>");
            mailSender.send(mimeMessage);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
