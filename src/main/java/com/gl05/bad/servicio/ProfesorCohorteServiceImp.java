package com.gl05.bad.servicio;

import com.gl05.bad.dao.ProfesorCohorteDao;
import com.gl05.bad.domain.Asignatura;
import com.gl05.bad.domain.AspiranteProfesor;
import com.gl05.bad.domain.Cohorte;
import com.gl05.bad.domain.ProfesorCohorte;
import com.gl05.bad.domain.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class ProfesorCohorteServiceImp implements ProfesorCohorteService {

    @Autowired
    private ProfesorCohorteDao profesorCohorteDao;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private UserServiceImp userServiceImp;
    
    @Autowired
    private AsignaturaService asignaturaService;
    
    @Override
    public ProfesorCohorte contratarP(ProfesorCohorte profesorCohorte,List<Long> idAsignaturas) {
        Cohorte cohorte = profesorCohorte.getIdCohorte();
        AspiranteProfesor aspiranteProfesor = profesorCohorte.getIdAspiranteProfesor();
        Long idUser = Long.valueOf(aspiranteProfesor.getIdusuario());
        Usuario usuario = userServiceImp.encontrarUsuario(idUser);
        StringBuilder textAsignaturas = new StringBuilder();
        for (Long idAsignatura : idAsignaturas) {
            Asignatura asignatura = new Asignatura();
            asignatura.setIdAsignatura(idAsignatura);
            Asignatura a = asignaturaService.encontrarA(asignatura);
            textAsignaturas.append(a.getNombreMateria()).append(", ");
        }
        String asunto= "Contratación en cohorte";
        String mensaje= "¡Felicidades!  Ha sido contratado en la cohorte " +cohorte.getNombreCohorte()+", Para las asignaturas de: "+textAsignaturas+".";
        ProfesorCohorte profesor = profesorCohorteDao.save(profesorCohorte);
        enviarCorreo(usuario.getEmail(), asunto, mensaje);
        return profesor;
    }
    
    public void enviarCorreo(String correoDestino, String asunto, String mensaje){
      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setTo(correoDestino);
      mailMessage.setSubject(asunto);
      mailMessage.setText(mensaje);
      mailSender.send(mailMessage);
    };

    @Override
    public List<ProfesorCohorte> listaProfesoresC(Long idCohorte) {
        return (List<ProfesorCohorte>) profesorCohorteDao.findProfesByCohorteId(idCohorte);
    }

    @Override
    public void eliminarPC(ProfesorCohorte profesorCohorte) {
        profesorCohorteDao.delete(profesorCohorte);
    }

    @Override
    public ProfesorCohorte encontrarProfesor(Long profesor) {
        return profesorCohorteDao.findById(profesor).orElse(null);
    }
    @Override
    public void actualizarProfesor(ProfesorCohorte profesor) {
        // Verifica si el área de conocimiento existe en la base de datos
        if (profesorCohorteDao.existsById(profesor.getIdProfesor() )) {
            // Actualiza el área de conocimiento en la base de datos
            profesorCohorteDao.save(profesor);
        } else {
            // El área de conocimiento no existe, puedes lanzar una excepción o manejar el caso según tus necesidades
            throw new IllegalArgumentException("El profesor especificado no existe.");        
        }
    }

}
