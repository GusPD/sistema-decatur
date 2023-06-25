package com.gl05.bad.servicio;

import com.gl05.bad.dao.AspiranteProfesorDao;
import com.gl05.bad.dao.ProfesorCohorteDao;
import com.gl05.bad.domain.AspiranteProfesor;
import com.gl05.bad.domain.Usuario;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class AspiranteProfesorServiceImp implements AspiranteProfesorService{
  
    @Autowired
    private AspiranteProfesorDao aspiranteDao;
    
    @Autowired
    private ProfesorCohorteDao profesorCohorteDao;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Override
    @Transactional(readOnly=true)
    public List<AspiranteProfesor> listarAspirantes() {
        return (List<AspiranteProfesor>)aspiranteDao.findAll();
    }
    
    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<AspiranteProfesor> listarAspirantes(DataTablesInput input) {
        return (DataTablesOutput<AspiranteProfesor>)aspiranteDao.findAll(input);
    }

    @Override
    @Transactional
    public void agregarAP(AspiranteProfesor aspirante) {
        aspiranteDao.save(aspirante);
    }
    
    @Override
    @Transactional
    public void actualizarAP(AspiranteProfesor aspirante) {
        AspiranteProfesor aspiranteExistente = aspiranteDao.findById(aspirante.getIdAspiranteProfesor()).orElse(null);
        aspiranteExistente.setNombresAp(aspirante.getNombresAp());
        aspiranteExistente.setApellidosAp(aspirante.getApellidosAp());
        aspiranteExistente.setSexoAp(aspirante.getSexoAp());
        aspiranteExistente.setGeneroAp(aspirante.getGeneroAp());
        aspiranteExistente.setFechaNacAp(aspirante.getFechaNacAp());
        aspiranteExistente.setEstadoCivilAp(aspirante.getEstadoCivilAp());
        aspiranteExistente.setIdPais(aspirante.getIdPais());
        aspiranteExistente.setNacionalidadAp(aspirante.getNacionalidadAp());
        aspiranteExistente.setDuiAp(aspirante.getDuiAp());
        aspiranteExistente.setNitAp(aspirante.getNitAp());
        aspiranteExistente.setNupAp(aspirante.getNupAp());
        aspiranteExistente.setPasaporteAp(aspirante.getPasaporteAp());
        aspiranteExistente.setDocPersonalAp(aspirante.getDocPersonalAp());
        
        Blob fotoActual = aspiranteExistente.getFotografiaAp();
        
        if (aspirante.getFotografiaAp() == null && fotoActual != null) {
          byte[] fotoBytes;
          try {
            fotoBytes = fotoActual.getBytes(1, (int) fotoActual.length());
            aspiranteExistente.setFotografiaAp(new javax.sql.rowset.serial.SerialBlob(fotoBytes));
          } catch (SQLException ex) {
            Logger.getLogger(AtestadoTaServiceImp.class.getName()).log(Level.SEVERE, null, ex);
          }
        } else {
          aspiranteExistente.setFotografiaAp(null);
        }
        
        aspiranteDao.save(aspiranteExistente);
    }

    @Override
    public void eliminarAP(AspiranteProfesor aspirante) {
        aspiranteDao.delete(aspirante);
    }

    @Override
    public AspiranteProfesor encontrarAP(AspiranteProfesor aspirante) {
        return aspiranteDao.findById(aspirante.getIdAspiranteProfesor()).orElse(null);
    }
    
    @Override
    @Transactional
    public void actualizarFoto(AspiranteProfesor aspirante, String nombreCampo, Blob valorCampo) {
        AspiranteProfesor coordinadorExistente = aspiranteDao.findById(aspirante.getIdAspiranteProfesor()).orElse(null);
        switch (nombreCampo) {
          case "fotografiaCa":
            coordinadorExistente.setFotografiaAp(valorCampo);
            break;}
        aspiranteDao.save(coordinadorExistente);
    }
    
    @Override
    @Transactional(readOnly=true)
    public AspiranteProfesor encontrarPorIdUsuario(Integer idUsuario) {
        return aspiranteDao.findByIdusuario(idUsuario);
    }
    
    @Override
    public String generarPassword(int length) {
        String lowercaseCharacters = "abcdefghijklmnopqrstuvwxyz";
        String uppercaseCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numericCharacters = "0123456789";
        String specialCharacters = "!@#$%^&*()-_=+[]{}\\|;:'\",.<>/?";

        String allCharacters = lowercaseCharacters + uppercaseCharacters + numericCharacters + specialCharacters;
        List<String> characterList = Arrays.asList(allCharacters.split(""));
        Collections.shuffle(characterList);

        StringBuilder passwordBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characterList.size());
            passwordBuilder.append(characterList.get(randomIndex));
        }

        return passwordBuilder.toString();
    }
    
    @Override
    public String buscarPerfil(String username) {
        String usuarioAspirante="";
         String idUsuario="";
         List<Usuario> usuarios=userService.listaUsuarios();
         for (Usuario usuario: usuarios) {
                if(usuario.getUsername().equals(username)){
                    idUsuario=usuario.getIdUsuario().toString();
                }
         }
         List<AspiranteProfesor> aspirantes= listarAspirantes();
         for (AspiranteProfesor aspirante: aspirantes) {
                if(aspirante.getIdusuario().toString().equals(idUsuario)){
                    usuarioAspirante=aspirante.getIdAspiranteProfesor().toString();
                }
         }

        return usuarioAspirante;
    }
    
    @Override
    @Transactional
    public void enviarCorreo(String correoDestino, String asunto, String mensaje){
      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setTo(correoDestino);
      mailMessage.setSubject(asunto);
      mailMessage.setText(mensaje);

      mailSender.send(mailMessage);
    };
    
    @Override
    public List<String> obtenerCorreosAspiranteProfesor() {
        List<AspiranteProfesor> aspirantes = listarAspirantes();
        List<AspiranteProfesor> idsProfesorCohorte = profesorCohorteDao.findIdsProfesorCohorte();
        List<Integer> idListCorreos = new ArrayList<>();
        List<String> correos = new ArrayList<>();

        for (AspiranteProfesor aspirante : aspirantes) {
            if (idsProfesorCohorte.contains(aspirante)) {
                idListCorreos.add(aspirante.getIdListCorreo());
            }
        }
        List<String> correosAsociados = aspiranteDao.findCorreosByIdListCorreos(idListCorreos);
        correos.addAll(correosAsociados);
        return correos;
    }
    
    @Override
    public List<String> obtenerCorreosProfesor() {
        List<AspiranteProfesor> aspirantes = listarAspirantes();
        List<AspiranteProfesor> idsProfesorCohorte = profesorCohorteDao.findIdsProfesorCohorte();
        List<Integer> idListCorreos = new ArrayList<>();
        List<String> correos = new ArrayList<>();

        for (AspiranteProfesor aspirante : aspirantes) {
            if (idsProfesorCohorte.contains(aspirante)) {
                idListCorreos.add(aspirante.getIdListCorreo());
            }
        }

        List<String> correosAsociados = aspiranteDao.findCorreosByIdListCorreos(idListCorreos);
        correos.addAll(correosAsociados);
        return correos;
    }
}
