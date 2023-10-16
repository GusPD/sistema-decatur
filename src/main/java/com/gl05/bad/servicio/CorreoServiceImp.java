package com.gl05.bad.servicio;

import com.gl05.bad.dao.CorreoDao;
import com.gl05.bad.domain.ConfiguracionCorreo;
import com.gl05.bad.domain.Correo;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CorreoServiceImp implements CorreoService{
  
    @Autowired
    private CorreoDao correoDao;
    
    @Autowired
    private ConfiguracionCorreoServiceImp configuracionCorreoService;
    
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public List<Correo> listarCorreos() {
        return (List<Correo>) correoDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(Correo correo) {
        correoDao.save(correo);
    }
    
    @Override
    @Transactional
    public void actualizar(Correo correo) {
        Correo correoExistente = correoDao.findById(correo.getIdCorreo()).orElse(null);
        correoDao.save(correoExistente);
    }

    @Override
    public void eliminar(Correo correo) {
        correoDao.delete(correo);
    }

    @Override
    public Correo encontrar(Correo correo) {
        return correoDao.findById(correo.getIdCorreo()).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Correo encontrarCorreo(String correo) {
        return correoDao.findByCorreo(correo);
    }
    
    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Correo> listarCorreos(DataTablesInput input, Long idPropietario) {
        Specification<Correo> specification = (root, query, builder) -> {
            return builder.equal(root.get("idPropietario"), idPropietario);
        };        
        return correoDao.findAll(input, specification);
    }
    
    @Override
    public boolean enviarCorreo(String destinatario, String asunto, String mensaje) {
        JavaMailSenderImpl mailSender = (JavaMailSenderImpl) javaMailSender;

        // Obtener la configuración de correo desde la base de datos
        ConfiguracionCorreo configuracionCorreo = configuracionCorreoService.obtenerConfiguracionCorreo();

        // Verificar si se pudo obtener la configuración de correo
        if (configuracionCorreo != null) {
            mailSender.setHost(configuracionCorreo.getHost());
            mailSender.setPort(Integer.parseInt(configuracionCorreo.getPort()));
            mailSender.setUsername(configuracionCorreo.getUsername());
            mailSender.setPassword(configuracionCorreo.getPassword());
            Properties javaMailProperties = mailSender.getJavaMailProperties();
            javaMailProperties.put("mail.transport.protocol", configuracionCorreo.getProtocol());
            javaMailProperties.put("mail.smtp.auth", String.valueOf(configuracionCorreo.getSmtp_auth()));
            javaMailProperties.put("mail.smtp.starttls.enable", String.valueOf(configuracionCorreo.getStart_tls()));
        }

        MimeMessage correo = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(correo, true);
            helper.setFrom(configuracionCorreo.getUsername(), configuracionCorreo.getName());
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(mensaje, true); // Habilita el soporte para HTML

            mailSender.send(correo);
            
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            return false;
        }
    }
}
